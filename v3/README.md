# Big 5 성격 검사 모놀리식 애플리케이션 v3

<img width="863" alt="image" src="https://github.com/user-attachments/assets/10ed88a5-f5b0-4289-9034-bdfb6b66ccd1">


## Python Fast API로 REST API 만들기
1. Python3 설치

https://www.python.org/downloads/

2. fastapi, pydantic 라이브러리 설치

```shell
pip3 install fastapi pydantic
```

3. main.py 작성

```python3
from fastapi import FastAPI
from pydantic import BaseModel

# Rest API 서버
app = FastAPI()

# API Request DTO
class BigFiveEvaluation(BaseModel):
    evaluationId: int
    extraversion: str
    neuroticism: str
    conscientiousness: str
    agreeableness: str
    openness: str
    
# REST API
@app.post("/api/commentaries")
def create_commentary(item: BigFiveEvaluation):
    # 더미 해설 반환
    return {"comments": """
            요약:
            Big Five 성격 특성 검사 결과에 따르면, 외향성은 중간 수준이며, 신경성은 중상 수준, 성실성은 중상 수준, 친화성은 낮음 수준, 개방성은 낮음 수준입니다.
            각 요인별 해석:
            1. 외향성: 중간 수준 - 당신은 다른 사람들과 함께 있는 것을 즐기며, 다양한 사회적 활동을 좋아합니다. 그러나 외향성이 높지 않기 때문에 혼자 있는 시간도 필요합니다.
            2. 신경성: 중상 수준 - 당신은 감정적으로 민감하며, 스트레스에 민감하게 반응할 수 있습니다. 이로 인해 불안이나 우울감을 느낄 수 있습니다.
            3. 성실성: 중상 수준 - 당신은 책임감이 강하고, 목표 지향적입니다. 계획을 세우고 실행하는 능력이 뛰어나며, 체계적이고 조직적인 성향을 가지고 있습니다.
            4. 친화성: 낮음 수준 - 당신은 다른 사람들과의 관계에서 독립적이고 자기주장이 강할 수 있습니다. 다른 사람들의 의견에 민감하지 않고, 자신의 의견을 확고하게 표현할 수 있습니다.
            5. 개방성: 낮음 수준 - 당신은 새로운 경험에 대한 호기심이 적고, 안정적이고 예측 가능한 환경을 선호합니다. 새로운 아이디어나 변화에 대한 수용력이 낮을 수 있습니다.
            개선을 위한 제안:
            1. 신경성: 스트레스를 관리하기 위해 명상, 요가, 운동 등의 휴식 기술을 도입해보세요. 또한, 자신의 감정을 인식하고 표현하는 방법을 연습하여 감정적인 안정을 찾을 수 있습니다.
            2. 친화성: 다른 사람들과의 관계를 개선하기 위해 적극적으로 대화하고, 다른 사람들의 의견을 존중하는 자세를 가지세요. 공감능력을 향상시키는 연습을 통해 친화성을 높일 수 있습니다.
            3. 개방성: 새로운 경험에 도전하고, 호기심을 갖도록 노력해보세요. 독서, 여행, 예술 감상 등 다양한 활동을 통해 새로운 아이디어나 관점을 접할 수 있습니다.
            
            지향하는 방향과 다른 결과에 대해 걱정하지 마세요. 이러한 결과는 당신의 성격을 이해하는 데 도움을 주는 도구일 뿐입니다. 자기 성장을 위해 약점을 보완하고 강점을 활용하는 노력을 계속하면 됩니다.
            """}
```

4. 성격 해설 서비스 실행
```shell
fastapi dev main.py
```

5. REST API 테스트

<img width="1325" alt="image" src="https://github.com/user-attachments/assets/9cdacecf-3bec-4099-aca6-bc691183deab">

## BFF에서 성격 해설 서비스 API 호출하기
1. PersonalityTestService.java 수정
```java
package fastcampus.bigfive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.entities.Evaluation;
import fastcampus.bigfive.repositories.EvaluationRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonalityTestService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation evaluate(LinePoints linePoints) {
        // 평가 하기
        Evaluation evaluation = new Evaluation();
        evaluation.evaluate(linePoints);
        
        // DB에 저장
        evaluationRepository.save(evaluation);
        return evaluation;
    }    
}
```

2. Evaluation.java 수정
```java
package fastcampus.bigfive.entities;

import fastcampus.bigfive.dtos.LinePoints;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "evaluations")
@ToString
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    

    /**
     * 외향성
     */
    private String extraversion;
    private int extraversionValue;      

    /**
     * 신경성
     */
    private String neuroticism;
    private int neuroticismValue;      

    /**
     * 성실성
     */
    private String conscientiousness;
    private int conscientiousnessValue;

    /**
     * 친화성
     */
    private int agreeablenessValue;
    private String agreeableness;
    
    /**
     * 개방성
     */
    private int opennessValue;
    private String openness;

    /**
     * 항목 포인트를 기준으로 5가지 평가값을 구한다
     * @param linePoints
     */
    public void evaluate(LinePoints linePoints) {
        evaluateExtraversion(linePoints);
        evaluateNeuroticism(linePoints);
        evaluateConscientiousness(linePoints);
        evaluateAgreeableness(linePoints);
        evaluateOpenness(linePoints);
    }

    /**
     * 개방성을 평가한다
     * @param linePoints
     */
    private void evaluateOpenness(LinePoints linePoints) {
        int sum = linePoints.getLinePoint3() + linePoints.getLinePoint8() + linePoints.getLinePoint11();

        if(sum <= 8) {
            opennessValue = 1;
            openness = "낮음";
        } else if(sum >= 9 && sum <= 10) {
            opennessValue = 2;
            openness = "중하";
        } else if(sum >= 11 && sum <= 12) {
            opennessValue = 3;
            openness = "중상";
        } else if(sum >= 13 && sum <= 15) {
            opennessValue = 4;
            openness = "높음";
        } else {
            opennessValue = 4;
            openness = "높음";
        }
    }

    /**
     * 친화성을 평가한다
     * @param linePoints
     */
    private void evaluateAgreeableness(LinePoints linePoints) {
        int sum = linePoints.getLinePoint2() + linePoints.getLinePoint7() + linePoints.getLinePoint12();

        if(sum <= 10) {
            agreeablenessValue = 1;
            agreeableness = "낮음";
        } else if(sum >= 11 && sum <= 12) {
            agreeablenessValue = 2;
            agreeableness = "중하";
        } else if(sum == 13) {
            agreeablenessValue = 3;
            agreeableness = "중상";
        } else if(sum >= 14 && sum <= 15) {
            agreeablenessValue = 4;
            agreeableness = "높음";
        } else {
            agreeablenessValue = 4;
            agreeableness = "높음";
        }
    }

    /**
     * 성실성을 평가한다
     * @param linePoints
     */
    private void evaluateConscientiousness(LinePoints linePoints) {
        int sum = linePoints.getLinePoint4() + linePoints.getLinePoint9();
        switch (sum) {
            case 2, 3, 4:
                conscientiousnessValue = 1;
                conscientiousness = "낮음";
                break;
            case 5, 6:
                conscientiousnessValue = 2;
                conscientiousness = "중간";
                break;
            case 7, 8:
                conscientiousnessValue = 3;
                conscientiousness = "중상";
                break;
            case 9, 10:
                conscientiousnessValue = 4;
                conscientiousness = "높음";
                break;
            default:
                conscientiousnessValue = 1;
                conscientiousness = "낮음";
                break;
        }   
    }

    /**
     * 신경성을 평가한다
     * @param linePoints
     */
    private void evaluateNeuroticism(LinePoints linePoints) {
        int sum = linePoints.getLinePoint5() + linePoints.getLinePoint10();
        switch (sum) {
            case 2, 3, 4:
                neuroticismValue = 1;
                neuroticism = "낮음";
                break;
            case 5, 6:
                neuroticismValue = 2;
                neuroticism = "중간";
                break;
            case 7, 8:
                neuroticismValue = 3;
                neuroticism = "중상";
                break;
            case 9, 10:
                neuroticismValue = 4;
                neuroticism = "높음";
                break;
            default:
                neuroticismValue = 1;
                neuroticism = "낮음";
                break;
        }   
    }

    /**
     * 외향성을 평가한다.
     * @param linePoints
     */
    private void evaluateExtraversion(LinePoints linePoints) {
        int sum = linePoints.getLinePoint1() + linePoints.getLinePoint6();
        switch (sum) {
            case 2, 3, 4:
                extraversionValue = 1;
                extraversion = "낮음";
                break;
            case 5, 6:
                extraversionValue = 2;
                extraversion = "중간";
                break;
            case 7, 8:
                extraversionValue = 3;
                extraversion = "중상";
                break;
            case 9, 10:
                extraversionValue = 4;
                extraversion = "높음";
                break;
            default:
                extraversionValue = 1;
                extraversion = "낮음";
                break;
        }
    }

    // Getter, Setter
    public Long getId() {
        return Id;
    }

    public int getExtraversionValue() {
        return extraversionValue;
    }
    
    public int getNeuroticismValue() {
        return neuroticismValue;
    }
    
	public int getConscientiousnessValue() {
        return conscientiousnessValue;
    }
    
    public int getAgreeablenessValue() {
        return agreeablenessValue;
    }
    
    public int getOpennessValue() {
        return opennessValue;
    }

    public String getExtraversion() {
        return extraversion;
    }

    public String getNeuroticism() {
        return neuroticism;
    }

    public String getConscientiousness() {
        return conscientiousness;
    }

    public String getAgreeableness() {
        return agreeableness;
    }

    public String getOpenness() {
        return openness;
    }
}
```

3. Contoller 수정
```java
package fastcampus.bigfive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fastcampus.bigfive.adapters.CommentaryRequest;
import fastcampus.bigfive.adapters.CommentaryServiceAdapter;
import fastcampus.bigfive.dtos.EvaluationResult;
import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.services.PersonalityTestService;

@RestController
public class TestRestController {

    @Autowired
    private PersonalityTestService personalityTestService;

    @Autowired
    private CommentaryServiceAdapter commentaryServiceAdapter;

    @PostMapping("/api/tests")
    public EvaluationResult test(@RequestBody LinePoints linePoints) {
        // 성격 평가
        var evaluation = personalityTestService.evaluate(linePoints);

        // Commentary Service로 해설 요청
        var commentaryRequest = CommentaryRequest.builder()
            .evaluationId(evaluation.getId())
            .agreeableness(evaluation.getAgreeableness())
            .conscientiousness(evaluation.getConscientiousness())
            .extraversion(evaluation.getExtraversion())
            .neuroticism(evaluation.getNeuroticism())
            .openness(evaluation.getOpenness())
            .build();
        String comments = commentaryServiceAdapter.query(commentaryRequest);
      
        // API 응답 값 생성
        var result = EvaluationResult.builder()
            .extraversionValue(evaluation.getExtraversionValue())
            .neuroticismValue(evaluation.getNeuroticismValue())
            .conscientiousnessValue(evaluation.getConscientiousnessValue())
            .agreeablenessValue(evaluation.getAgreeablenessValue())
            .opennessValue(evaluation.getOpennessValue())
            .commentary(comments)
            .build();

        return result;
    }
}
```

4. CommentaryServiceAdapter.java 작성

```java
package fastcampus.bigfive.adapters;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommentaryServiceAdapter {
    public String query(CommentaryRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        CommentaryResponse response = restTemplate.postForObject("http://localhost:8000/api/commentaries", request, CommentaryResponse.class);
        return response.getComments();
    }
}
```

5. CommentaryRequest.java 작성
```java
package fastcampus.bigfive.adapters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentaryRequest {
    private Long evaluationId;      
    private String extraversion;      
    private String neuroticism;      
    private String conscientiousness;
    private String agreeableness;
    private String openness;
}
```

6. CommentaryResponse.java 작성
```java
package fastcampus.bigfive.adapters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentaryResponse {
    private String comments;
}
```

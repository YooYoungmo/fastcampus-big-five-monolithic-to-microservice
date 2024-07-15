# Big 5 성격 검사 모놀리식 애플리케이션 v2

<img width="772" alt="image" src="https://github.com/user-attachments/assets/0fbc7659-250e-415a-83c8-c5d281dc9464">

## REST API로 백엔드 서비스 전환하기
1. TestRestController.java 코드 작성

```java
package fastcampus.bigfive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fastcampus.bigfive.dtos.EvaluationResult;
import fastcampus.bigfive.dtos.LinePoints;
import fastcampus.bigfive.services.PersonalityTestService;

@RestController
public class TestRestController {

    @Autowired
    private PersonalityTestService personalityTestService;

    @PostMapping("/api/tests")
    public EvaluationResult test(@RequestBody LinePoints linePoints) {
        var evaluation = personalityTestService.evaluate(linePoints);

        var result = EvaluationResult.builder()
        .extraversionValue(evaluation.getExtraversionValue())
        .neuroticismValue(evaluation.getNeuroticismValue())
        .conscientiousnessValue(evaluation.getConscientiousnessValue())
        .agreeablenessValue(evaluation.getAgreeablenessValue())
        .opennessValue(evaluation.getOpennessValue())
        .commentary(evaluation.getCommentary().getComments())
        .build();

        return result;
    }
}
```

2. [Postman](https://www.postman.com/) 으로 API 테스트

<img width="1304" alt="image" src="https://github.com/user-attachments/assets/22914472-9134-4733-a885-354fec1b1854">


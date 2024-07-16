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

2. [Postman](https://www.postman.com/) API 테스트

<img width="1304" alt="image" src="https://github.com/user-attachments/assets/22914472-9134-4733-a885-354fec1b1854">

## Next.js 기반의 프론트엔드 서비스 개발
1. [Node.js](https://nodejs.org/en/) 설치
2. Next.js 프로젝트 생성

https://nextjs.org/docs/getting-started/installation

```shell
npx create-next-app@latest
```

3. Next.js 애플리케이션 실행

```shell
npm run dev
```

4. Ant Design 설치

https://ant.design/docs/react/use-with-next

```shell
npm install antd --save
```

5. LineItem.tsx 컴포넌트 작성

```tsx
import {Form, Radio, Space, Typography} from "antd";

const { Title } = Typography;

export function LineItem({name, label, reversePoint}: { name: string, label: string, reversePoint?: boolean }) {
  return (
    <Form.Item name={name} colon={false} labelCol={{span: 24}}
               label={<Title level={5}>{label}</Title>}
               validateStatus="error"
               rules={[{
                 required: true,
                 message: "항목을 선택해 주세요.",
               }]}
    >
      <Space>
        <span>전혀 아니다</span>
        <Radio.Group>
          {reversePoint ?
            <>
              <Radio value="5" style={{flexDirection: "column-reverse", paddingBottom: "40px",  paddingLeft: "5px"}}>1</Radio>
              <Radio value="4" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>2</Radio>
              <Radio value="3" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>3</Radio>
              <Radio value="2" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>4</Radio>
              <Radio value="1" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>5</Radio>
            </>
          : <>
              <Radio value="1" style={{flexDirection: "column-reverse", paddingBottom: "40px",  paddingLeft: "5px"}}>1</Radio>
              <Radio value="2" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>2</Radio>
              <Radio value="3" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>3</Radio>
              <Radio value="4" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>4</Radio>
              <Radio value="5" style={{flexDirection: "column-reverse", paddingLeft: "5px"}}>5</Radio>
            </>}
        </Radio.Group>
        <span>매우 그렇다</span>
      </Space>
    </Form.Item>
  );
}

export default LineItem;
```

6. PersonalityTestForm.tsx 작성
```tsx
import { Button, Divider, Form, Typography } from "antd";
import { useForm } from "antd/es/form/Form";
import {MonitorOutlined} from "@ant-design/icons";
import LineItem from "./LineItem";

const {Title} = Typography;

export default function PersonalityTestForm({onFinish}: { onFinish: (values: any) => void }) {
    const [form] = useForm();
  
    const handleFinish = (values: any) => {
      onFinish(values);
      form.resetFields();
    }
  
    return (
      <main className="flex min-h-screen flex-col items-center justify-between p-5">
        <Title level={1}>성격 진단표</Title>
        <Divider/>
        <Form form={form} scrollToFirstError={true} className="text-center" onFinish={handleFinish}>
          <LineItem name="linePoint1" label="1. 모르는 사람에게 먼저 말은 건다"/>
          <LineItem name="linePoint2" label="2. 다른 사람이 편안하고 행복한지 확인한다"/>
          <LineItem name="linePoint3" label="3. 그림, 글, 음악을 창작한다"/>
          <LineItem name="linePoint4" label="4. 모든 일을 사전에 준비한다"/>
          <LineItem name="linePoint5" label="5. 울적하거나 우울함을 느낀다"/>
          <LineItem name="linePoint6" label="6. 회식, 파티, 사교모임을 계획한다"/>
          <LineItem name="linePoint7" label="7. 사람들을 모욕한다" reversePoint={true}/>
          <LineItem name="linePoint8" label="8. 철학적이거나 영적인 문제들을 생각한다"/>
          <LineItem name="linePoint9" label="9. 일이나 물건을 정리하지 않고 어지럽게 그냥 둔다" reversePoint={true}/>
          <LineItem name="linePoint10" label="10. 스트레스나 걱정을 느낀다"/>
          <LineItem name="linePoint11" label="11. 어려운 단어를 사용한다"/>
          <LineItem name="linePoint12" label="12. 타인의 감정에 공감한다"/>
          <div className="text-center">
            <Button type="primary" htmlType="submit" icon={<MonitorOutlined/>}>
              제출
            </Button>
          </div>
        </Form>
      </main>
    );
  }
```

7. page.tsx 에서 PersonalityTestForm 사용
```tsx
'use client';
import PersonalityTestForm from "@/components/PersonalityTestForm";

export default function Home() {

  const handleOnFinish = (values: any) => {
    console.log("values", values);
  }

  return (
    <>
      <PersonalityTestForm onFinish={handleOnFinish} />
    </>
  );
}
```

8. Backend REST API 호출

page.tsx  변경

```tsx
'use client';
import PersonalityTestForm from "@/components/PersonalityTestForm";

export default function Home() {

  const handleOnFinish = async (values: any) => {
    // API Request 형태로 변환
    const linePoints: any = {}
    for (const property in values) {
      linePoints[property] = Number(values[property]);
    }

    // API 호출
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_ENDPOINT}/tests`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(linePoints),
    })

    // API Response 값을 JSON으로 변환
    const evaluation = await res.json();
    console.log("evaluation", evaluation);
  }

  return (
    <>
      <PersonalityTestForm onFinish={handleOnFinish} />
    </>
  );
}
```
.env 파일 생성 및 작성
```
NEXT_PUBLIC_API_ENDPOINT=http://localhost:8080/api
```

![image](https://github.com/user-attachments/assets/a7aab594-f651-422d-a51a-37760c62b548)

![image](https://github.com/user-attachments/assets/084ece73-2064-469a-99cd-f2eb67683345)


9. CORS(Cross-Origin Resource Sharing) 문제 해결
https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS

Backend에 WebConfig.java 작성
```java
package fastcampus.bigfive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @SuppressWarnings("null")
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000");
  }
}
```
10. 로딩 화면 추가
```tsx

```

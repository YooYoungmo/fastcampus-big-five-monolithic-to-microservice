# Big 5 성격 검사 모놀리식 애플리케이션

<img width="719" alt="image" src="https://github.com/user-attachments/assets/20366714-fceb-4a18-bf2a-1ff7bc55f345">


## 애플리케이션 동작 전제 조건
### JDK 21 설치
아래 링크에서 컴퓨터 플랫폼에 맞는 버전을 내려받아 설치

https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html

### Solar LLM API 키 발급

1 회원 가입

https://console.upstage.ai/

![image](https://github.com/user-attachments/assets/6da046a1-6823-40ad-8b8a-b0522dc1b5ae)


2 좌측 메뉴 > API Key/ Example로 이동 후 'Create API Key' 버튼 클릭

![image](https://github.com/user-attachments/assets/ca392b45-5b8a-4221-91ec-4abfc9b9deac)


## 애플리케이션 실행
### 환경 변수 선언
```shell
export SOLAR_API_KEY=...
# or 
set SOLAR_API_KEY=...
```
### 실행 명령어
```shell
./mvnw spring-boot:run 
```
## 계층형 아키텍처(Layered Architecture)

![image](https://github.com/user-attachments/assets/cd6ffa6d-3b7b-4713-a614-cbf565988b28)

![image](https://github.com/user-attachments/assets/af1229cd-cef5-43f8-892b-af6772cf5e05)



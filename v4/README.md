# Big 5 성격 검사 애플리케이션 v4
<img width="1372" alt="image" src="https://github.com/user-attachments/assets/c10aaa5b-91bc-43cf-8f00-a53c2f58fc57">

## Go 초경량 마이크로서비스 만들기
1. 도커 데스크탑 설치

https://www.docker.com/products/docker-desktop/

2. Dockerfile 생성
```Dockerfile
FROM golang:1.22.5
WORKDIR /go/src/evaluation-service
COPY . .
RUN go mod download
RUN go install -ldflags '-w -extldflags "-static"'
WORKDIR /go/bin/
EXPOSE 2016
CMD ["./evaluation-service"]
```

3. Docker 이미지 생성
```shell
docker build --tag evaluation-service:0.1 .
```

4. 결과물 확인
<img width="1414" alt="image" src="https://github.com/user-attachments/assets/faa27500-042f-45eb-ada3-37a0bf256b89">

## [Multi-stage builds](https://docs.docker.com/build/building/multi-stage/)로 이미지 용량 줄이기
1. Dockerfile 수정
```Dockerfile
FROM golang:1.22.5 AS builder
WORKDIR /go/src/evaluation-service
COPY . .
RUN go mod download
RUN go install -ldflags '-w -extldflags "-static"'

# make application docker image use alpine
FROM alpine:3.10
WORKDIR /go/bin/

# copy execute file to image
COPY --from=builder /go/bin/evaluation-service .
EXPOSE 2016
CMD ["./evaluation-service"]
```

2. Docker 이미지 생성
```shell
docker build --tag evaluation-service:0.2 .
```

3. 결과물 확인
<img width="1433" alt="image" src="https://github.com/user-attachments/assets/b46dfdb8-2427-49b2-ae91-ef463270ed6f">

<img width="1381" alt="image" src="https://github.com/user-attachments/assets/640a88d8-648f-41fe-bf29-5e20e84185be">


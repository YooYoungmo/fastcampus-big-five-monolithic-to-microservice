FROM golang:1.22.5 AS builder
WORKDIR /go/src/evaluation-service
COPY . .
RUN go mod download
RUN go install -ldflags '-w -extldflags "-static"'

# make application docker image use alpine
FROM alpine:3.10
# using timezone
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apk add -U tzdata

WORKDIR /go/bin/

# copy execute file to image
COPY --from=builder /go/bin/evaluation-service .
EXPOSE 2016
CMD ["./evaluation-service"]

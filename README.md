# smilegate camp 2022 devpet
스마일게이트 개발 캠프 2022 - 윈터 개발 캠프 2기 - devpet

---
 <img src= "https://user-images.githubusercontent.com/32475430/221047363-d8734e76-d675-42ec-8727-4078e5b0fa67.png" width="800px"/>

## 👨‍👩‍👧‍👦 팀 역할 👨‍👧‍👦 
|이름|김동훈|안재진|오세환|채현수|
|:--|:-|:--|:--|:--|
|역할|Back-End|Back-End|Back-End|Front-End|
|업적|`chat 서버 구현` `neo4j 벌크 연산 처리` `feed 알고리즘 구현` `msa architechure 구축` `관계 서버 구축` `~테스트 서버 제공~`|`auth 서버 구현` `추천 로직 처리`, `feed 알고리즘 구현` |`app 서버 구현` `Devops`|`앱 구현`|

---

## 🧑‍💻 프로젝트 소개 🧑‍💻
### 프로젝트 개요
지역 기반 펫 커뮤니티를 만들자.

### 기술 스택
 # Frontend<br>
 `java`
 `android`
 `firebase (storage, fcm)`
<br>

 # Backend<br>
 ### oauth server
 * Java 1.8
 * Spring Boot 2.6.5
 * Spring Cloud 2021-05
 * Spring Data JPA
 * Spring Security
 * Lombok
 * MySQL 8.0.31
 * AWS EC2

 ### chat server
 * Java 11
 * SpringBoot 2.6.5
 * Spring Cloud 2021-05
 * Websocket
 * STOMP
 * Redis
 * AWS EC2

 ### app server
* Java 11
* SpringBoot 2.6.5
* Spring Cloud 2021-05
* Spring Quartz
* Spring Data Redis
* Spring Data MongoDB
* firebase fcm
* AWS EC2

 ### feed
 * Java 15
 * SpringBoot 2.6.5
 * Spring Cloud 2021-05
 * Spring Quartz
 * Spring Data Redis
 * Spring Data Neo4j
 * AWS EC2
<br>

 ### API Gateway
 * Spring Cloud 2021-05
 * Spring Cloud Starter Netflix Zuul

 ### Service Discovery
 * Spring Cloud 2021-05
 * Spring Cloud Netflix Eureka

## Directory

```
devpet
├──  docs
├──  backend
|    ├── app
|    ├── auth
|    ├── chat
|    ├── feed
|    ├── api-gateway
|    └── service-discovery
|    
|    
|
└── frontend/appsrc/main
    ├── java/com/example/petmilly
    |   ├── model
    |   ├── view
    |   └── viewModel
    └── res
        ├── anim
        ├── drawable
        ├── font
        ├── menu
        ├── values
        |   ├── theme
        |   ├── color
        |   └── string
        └── xml
                   
```
---

## 깃 규칙

|                   메시지                   |             설명             |
| ------------------------------------------ | ---------------------------- |
| feat (feature)                             |  새로운 기능 추가            |
| fix (bug fix)                              |  버그 수정                   |
| style (formatting, missing semi colons, …) |  코드 포맷팅                 |
| refactor                                   |  코드 리팩토링               |
| test (when adding missing tests)           |  테스트 관련                 |
| chore (maintain)                           |  기타 수정                   |

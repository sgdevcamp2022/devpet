# Getting Started Eureka

## application.yml
```yaml
server:
  port: 8761
 
spring:
  application:
    name: service-discovery
 
eureka:
  client:
    register-with-eureka: false # 유레카 서비스에 (자신을) 등록 여부
    fetch-registry: false  # (유레카 클라이언트 기반) 다른 서비스들의 정보를 fetch 유무
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/ # 설정을 하지 않으면 기본 http://localhost:8761/ 로 연동된다.
 
  server:
    wait-time-in-ms-when-sync-empty: 5  # 서버가 요청을 받기 전 대기할 초기 시간(ms) : 거의 대기 없음 (default 5분 : 상용에서는 모든 서비스가 등록 되길 기다리기 위해 5분 후 정보를 공유)
 
management:
  endpoints:
    web:
      exposure:
        include: "*"
```
### register-with-eureka 
- 유레카 서버에 클라이언트로서 등록할지 유무 입니다. 자기 자신이기에 여기서는 등록하지 않습니다. * 추후 유레카 서버 이중화 구성 포스팅에서 이 설정을 변경하도록 하겠습니다.
### fetch-registry 
- 다른 서비스의 정보를 유레카 서버로 가져와 (로컬메모리에) 캐싱할지 여부입니다. 유레카 서버이기에 false로 합니다.
### wait-time-in-ms-when-sync-empty 
- 유레카 서버는 등록된 서비스들의 정보를 공유한다고 하였습니다. 이 설정은 서비스들이 유레카 서버에 등록되기까지 얼마나 대기할지의 설정으로 기본값은 5분입니다. 즉 5분동안 서비스의 등록을 기다린 후 등록된 서비스의 정보를 공유 합니다. 개발환경에서는 기동후 빠른 테스트를 위해 5ms으로 값을 조정하였습니다.
### management 이하 설정 
- spring actuator의 설정입니다. 전 장에서 스프링 부트 운용을 위한 다양한 유틸성 API를 제공한다고 하였습니다. include: "*" 는 모든 API를 열겠다는 뜻입니다.

## 각 Service에서 설정 방법
```yaml
server:
  port: 8011
 
spring:
  application:
   name: service1
 
eureka:
  instance:
    prefer-ip-address: true
  client:
    register-with-eureka: true # 유레카 서비스에 (자신을) 등록 여부
    fetch-registry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
 
management:
  endpoints:
    web:
      exposure:
        include: "*"

```
- 여기서 중요한것이 "spring.application.name" 입니다. 
- 통신을 할때는 IP:PORT 대신 각 서비스의 고유ID, 즉 어플리케이션 이름으로 통신 합니다.
### prefer-ip-address 
- 유레카서버에 어플리케이션이름에 매핑되는 호스트명이 기본적으로 등록되게 됩니다. 
- 이 값의 설정으로 호스트명이 아닌 IP 주소를 대신 등록하겠다는 것입니다. 
- 그 이유는 컨테이너 기반의 MSA 환경에서 보통 DNS서버가 없기에 임의로 생성된 호스트 네임을 부여 받습니다. 이는 결국 생성된 호스트 네임의 정상적인 위치를 얻지 못할수 있기에 명확한 IP로 등록해 달라고 지정하는 것입니다.
### defaultZone 
- 등록할 유레카 서버의 위치를 지정합니다.

### eureka 설정은 크게 3가지로 분류 됩니다.
1. 디스커버리 서버 자체에 대한 설정
   eureka.server.*의 접두어로 시작하는 설정입니다.
   서버로서 어떻게 구성될지의 설정을 합니다. (* 즉 Eureka 서버의 프로퍼티 설정에 들어가는 내용입니다.)

2. 클라이언트 자체에 대한 설정
   eureka.instance.*의 접두어로 시작하는 설정입니다.
   클라이언트로서 어떻게 구성될지의 설정입니다.

3. 클라이언트 행위(Action) 설정
   eureka.client.*의 접두어로 시작하는 설정입니다.
   클라이언트가 연계할 유레카 서버 설정(defaultZone, register-with-eureka), 다른 서비스들의 정보를 가져오는(fetch-registry)등 행위에 대한 설정을 합니다.

## 코드 구현
```java
@SpringBootApplication
@EnableDiscoveryClient
public class Service1Application {
    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }
}
```
- 유레카 클라이언트로 적용할 서비스에 @EnableDiscoveryClient 어노테이션을 명시합니다.
- 

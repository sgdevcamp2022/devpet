# Getting Started Ribbon & Feign

## HTTP 통신 3가지 방식
- 일반 RestTemplate을 이용한 통신
- Ribbon을 탑재한 RestTemplate을 이용한 통신
- Feign을 이용한 통신

### RestTemplate
#### src/com/devpet/service1/rest/RestTemplateClientCommunicator
- RestTemplate 객체를 생성하여 IP와 PORT로 직접적으로 호출하는 구조입니다.
- RestTemplate를 직접 이용할 경우 다음의 문제가 있습니다. 
  - 호출하는 서비스에서  IP와 PORT를 관리해야 한다. (아니면 위 예제 처럼 DiscoveryClient를 통해 항상 정보를 가져온 뒤 호출해야 함)
  - 클라이언트 측 부하 분산을 할 수 없다. (Ribbon의 다양한 로드밸런싱 설정 사용 불가)
### 

### Ribbon을 탑재한 RestTemplate
- Ribbon은 spring-cloud-starter-netflix-eureka-client에 포함되어 있습니다.
```java
@SpringBootApplication
@EnableDiscoveryClient
public class Service1Application {
 
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    //... 중략 ...
}
```
- 우선 @EnableDiscoveryClient 어노테이션을 선언하여 DiscoveryClient를 활성하 합니다.
- 이후 RestTemplate을 Bean으로 등록한 뒤 @LoadBalanced 어노테이션을 달아줍니다. 
  - 이 설정으로 RestTemplate에 Ribbon의 탑재가 완료되었습니다.
#### src/com/devpet/service1/rest/RibbonClientCommunicator
- @Autowired 어노테이션으로 조금전 Bean으로 등록한 RestTemplate을 주입받습니다.
- 이번에는 IP:PORT 대신 서비스2의 고유명(service2)으로 호출하면 Ribbon이 내부적으로 IP와 PORT를 찾아서 처리해 줍니다.
- 서비스2가 여러개의 인스턴스로 이중화 구성되어 있다면 소프트웨어적 로드밸런싱 처리도 해줍니다.
- 추가 설정을 하지않는 경우 기본 설정인 Round Robbin 알고리즘으로 순차 로드밸런싱을 해줍니다.

### Fegin을 이용한 통신
- Feign을 사용하면 Java코드도 최대한 줄이고 Interface에 어노테이션 형식으로 간결하고 보다 쉽게 구현할 수 있습니다. 
```java
@SpringBootApplication
@EnableFeignClients
public class Service1Application {
 
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    //..... 중략 .....
}
```
- Service1Application 에 @EnableFeignClients 어노테이션으로 Feign Client임을 설정합니다.
```java
@FeignClient("service2")
public interface FeignClientCommunicator {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/name/{id}")
    String getName(@PathVariable("id") String id);
}
```
- @FeignClient("service2")로 서비스2로 API 호출한다고 명시 합니다.
- @RequestMapping는 GET 요청으로 "/name/{id}" API를 호출하겠다 입니다.

# Getting Started API-GATEWAY

## application.yml

```yaml
zuul:
  prefix:  /api
  routes:
    # 수동 경로 매핑  http://localhost:5555/srve2/v1/~~
    service2: /srve2/**

```
### zuul.prefix
- API 호출을 할 때 경로의 prefix를 지정해줍니다.
### zuul.routes
수동으로 경로 매핑을 하는 설정입니다. 위와 같이 설정을 하면 기본 URI인 "localhost:5555/api/service2/name/{id}" 이 아닌 "localhost:5555/api/srve2/name/{id}"로도 요청을 할수가 있습니다.
## Filter
```java
public class PreFilter extends ZuulFilter {
 
    @Override
    public String filterType() {
        return "pre";
    }
 
    @Override
    public int filterOrder() {
        return 1;
    }
 
    @Override
    public boolean shouldFilter() {
        return true;
    }
 
    @Override
    public Object run() {
        log.info("===== START Pre Filter. =====");
        return null;
    }
}

```
- 우선 filterType() 메소드를 오버라이드 하여 "pre" / "route" / "post" 문자열을 지정하여 해당 필터가 어떤 필터인지 결정합니다.
### Pre Filter
대상 서비스로 요청이 Routing 되기 전에 실행되는 필터입니다.
대상 서비스에 요청하기 전에 유효한 요청인지 인증(Authentication)이나 인가(Authorization)를 하거나, 요청 로깅, 요청의 유효성 검사, 공통 초기화 설정 등의 기능을 처리하기에 좋은 위치 입니다.

### Post Filter
대상 서비스를 호출하고 최종 응답을 클라이언트에 반환한 뒤에 호출되는 메서드 입니다.
응답 로깅을 하거나, 완료 후 처리되는 공통 로직 등의 기능을 넣기에 적절한 위치 입니다.

### Route Filter
따로 구현하지 않아도 Zuul 내부적으로 기본 Route Filter(RibbonRoutingFilter)가 작동하여 대상 서비스에 라우팅을 수행합니다.
Custom Route Filter를 구현하면 기본 라우팅이 수행되기 전에 호출을 가로채어 동적으로 원하는 Routing 기능을 구현 할 수 있습니다.
예를 들어 원본 대상 라우팅전에 반드시 거쳐야 하는 특정 서비스를 호출해야 한다거나, 카나리아 테스트를 위해 라우팅 대상 서비스를 동적으로 변환하여 라우팅하는 등의 기능을 구현할 수 있습니다.


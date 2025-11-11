# BookMarket (온라인 도서 쇼핑몰)

Spring Boot 기반으로 구현한 온라인 서점 웹 애플리케이션입니다.  
회원 관리, 로그인/로그아웃, 장바구니, 주문, 결제 기능 등을 포함하고 있습니다.

## 기술 스택
- Java 17
- Spring Boot 3.4.9
- Spring MVC / Spring Security / JPA
- Thymeleaf
- MariaDB
- Log4j2 (Monitoring / Auditing Log)
- Iamport 결제 API

## 주요 기능

### 1. 회원 관리
- 회원 가입 / 로그인 / 로그아웃
- BCryptPasswordEncoder 를 활용한 비밀번호 암호화
- UserDetailsService 커스텀 구현

### 2. 인증/인가
- ROLE_USER / ROLE_ADMIN 권한 분리
- 관리자만 상품 등록/수정 가능
- 일반 사용자는 주문/장바구니 기능 사용 가능

### 3. 주문 및 결제
- 장바구니 기반 주문 처리
- Iamport API 연동하여 실 결제 테스트 가능

### 4. 로그 및 모니터링
- MonitoringInterceptor / AuditingInterceptor 적용
- 로그 파일 자동 롤링 및 이벤트 추적

## 프로젝트를 통해 배운 점
- Spring Security 인증 구조를 직접 구성해보며 필터 체계를 이해할 수 있었습니다.
- JPA Entity 설계 및 Cascade 전략 적용 시 주의할 점을 학습했습니다.
- 결제 API와 같은 외부 서비스 연동 경험을 통해 실무적인 구조를 익혔습니다.
- Interceptor + Log4j2 기반 모니터링으로 요청 흐름을 추적하는 방법을 익혔습니다.

# soundscape-server

## 사용 기술
- Java 21
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- MySQL
- JWT (jjwt)
- REST Client: `RestClient`
- API 문서: springdoc-openapi (Swagger UI)

## 아키텍처
<img width="1899" height="981" alt="8 drawio" src="https://github.com/user-attachments/assets/70205814-6440-4a70-af02-d45357ceec17" />

- 동일 VPC 내에 **API 서버(Spring Boot)** 와 **추천 서버(FastAPI, LangGraph/LangServe)** 를 분리 배치
- API 서버는 인증/유저/플레이리스트/리뷰 등 **트랜잭션과 영속성(MySQL/JPA)** 을 담당.
- 추천/생성 로직은 외부 추천 서버에 위임, API 서버는 이를 HTTP로 호출해 결과를 저장/가공.

## 코드 구조(패키지)
- `com.soundscape.common`
  - 공통 설정(`JpaConfig`, `SwaggerConfig`, `RestClientConfig` 등)
  - 예외/응답 포맷(`CommonControllerAdvice`, `CommonResponse`, `ErrorCode`)
  - 인증(JWT) 인터셉터/유저 컨텍스트(`JwtAuthenticationInterceptor`, `UserContextHolder`)
- `com.soundscape.user`
  - 로그인/온보딩, `User` JPA 엔티티 및 리포지토리
- `com.soundscape.playlist`
  - 플레이리스트 생성/조회/이름 변경 등
  - 추천 서버 호출(`playlist.infra.engine.MusicEngineClient`)
  - Spotify 연동(playlist infra 및 common factory)
- `com.soundscape.reviews`
  - 리뷰 CRUD
- `com.soundscape.mypage`
  - 프로필/선호 아티스트/선호 장르 업데이트

## 인증(JWT)
- 로그인 성공 시 access token(JWT)을 발급.
- 인증이 필요한 API는 `Authorization: Bearer <token>` 헤더 필요.
- 인증 처리는 `HandlerInterceptor`(`JwtAuthenticationInterceptor`)에서 토큰을 파싱하고, 요청 스코프 컨텍스트에 유저 식별자를 보관하는 방식.

## 외부 연동
- Spotify Web API (spotify-web-api-java)
- Kakao OAuth
- 추천 서버(FastAPI): `soundscape.music-agent` 설정으로 엔드포인트를 주입, `RestClient`로 호출.
---
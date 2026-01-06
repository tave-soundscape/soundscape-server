package com.soundscape.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "로컬 서버"),
                @Server(url = "https://soundscape.higu.kr", description = "배포 서버")
        },
        info = @Info(
                title = "SoundScape API",
                description = "**SoundScape API**<br/><br/>"
                        + "**프론트엔드 테스트 시 꼭 아래 Servers에서 배포 서버로 설정해주세요**."
                        + "<br/><br/>"
                        + "**인증 관련**<br/>"
                        + " - 로그인을 제외한 모든 API는 JWT 토큰 인증이 필요합니다.<br/>"
                        + " - 아래 Authorize 버튼을 누르고 임시 JWT 토큰을 입력해주세요.<br/><br/>"
                        + "**임시 토큰**(유저 id 1인 테스트 유저의 계정으로 로그인 하는 것과 동일): <br/>"
                        + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzY2ODg3ODcyLCJleHAiOjE3OTg0MjM4NzJ9.SMEJfY6YTcfk3bYbnEoa3JHGlSj2Nm49BvZMWbCqoq-0kGN_5az_3GhuYOeP4-d6"
                        + "<br/>eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyIiwiaWF0IjoxNzY3Njg5OTI2LCJleHAiOjE3OTkyMjU5MjZ9.Il779gX-sxdSqyHqDFz9JDr5rBwTat_zq_YS68GRyvS9gW2Xia-bO9DKpBArp3OD"
                        + "<br/><br/>**임시 토큰을 사용하지 않고, 로그인 테스트 방법**<br/>"
                        + "1. api/v1/auth 경로로 GET 요청을 보내 카카오 로그인 URL을 받습니다.<br/>"
                        + "2. 받은 URL로 접속하여 카카오 로그인 후, 리다이렉트된 주소에서 code 파라미터 값을 복사합니다.<br/>"
                        + "3. api/v1/auth/login 경로로 POST 요청을 보내고, 응답에서 accessToken 값을 확인합니다.",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI openAPI() {
                // 1. JWT Security Scheme 정의
                String jwtSchemeName = "jwt토큰 설정";
                SecurityScheme securityScheme = new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer") // 스킴 타입 bearer
                        .bearerFormat("JWT"); // Bearer 포맷은 JWT

                // 2. Security Requirement 정의 (모든 API에 적용)
                SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

                // 3. Components에 Scheme 추가 및 전체 OpenAPI 객체에 Requirement 적용
                return new OpenAPI()
                        .components(new Components().addSecuritySchemes(jwtSchemeName, securityScheme))
                        .addSecurityItem(securityRequirement);
        }
}

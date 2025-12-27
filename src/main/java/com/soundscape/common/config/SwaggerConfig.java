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
                @Server(url = "http://3.37.62.220:8080", description = "배포 서버")
        },
        info = @Info(
                title = "SoundScape API",
                description = "**SoundScape API**<br/><br/>"
                        + "**프론트엔드 테스트 시 꼭 아래 Servers에서 배포 서버로 설정해주세요**."
                        + "<br/><br/>"
                        + "**인증 관련**<br/>"
                        + " - 로그인을 제외한 모든 API는 JWT 토큰 인증이 필요합니다.<br/>"
                        + " - 아래 Authorize 버튼을 누르고 임시 JWT 토큰을 입력해주세요.<br/><br/>"
                        + "**임시 토큰**: <br/>"
                        + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxMjM0NSIsImlhdCI6MTc2Njg1NjA2OCwiZXhwIjoxNzY2ODU5NjY4fQ.yAqLJA-JxJ4fKtfNfXtIfDiWJCESDen-NIRwJigyhCgDO8GD6u2sSR6i0QxBmvDS.<br/>",
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

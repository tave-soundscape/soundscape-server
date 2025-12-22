package com.soundscape.common.jwt;

import com.soundscape.common.auth.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    @Test
    void createToken() {
        JwtUtil jwtUtil = new JwtUtil("soundscape-jwt-secret-testsecretrandomstring7d2a4c6f8b", 60);
        String token = jwtUtil.createToken("12345");
        System.out.println(token);
    }

    @Test
    void getClaims() {
        JwtUtil jwtUtil = new JwtUtil("soundscape-jwt-secret-testsecretrandomstring7d2a4c6f8b", 60);
        String token = jwtUtil.createToken("12345");
        Jws<Claims> claims = jwtUtil.getClaims(token);
        Assertions.assertThat(claims.getPayload().getSubject()).isEqualTo("12345");
    }
}
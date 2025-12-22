package com.soundscape.common.auth.jwt;

import com.soundscape.common.auth.exception.CustomJwtException;
import com.soundscape.common.response.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long validityMinutes;

    public JwtUtil(@Value("${jwt.secret}") String secretString,
                   @Value("${jwt.access-token-exp}") long validityMinutes) {
        this.key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        this.validityMinutes = validityMinutes;
    }

    public String createToken(String subject) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMinutes * 60 * 1000);

        return Jwts.builder()
                .header()
                    .add("typ", "JWT")
                .and()
                .subject(subject)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new CustomJwtException(ErrorCode.JWT_EXPIRED_TOKEN);
        } catch (MalformedJwtException | SignatureException e) {
            throw new CustomJwtException(ErrorCode.JWT_INVALID_TOKEN);
        } catch (JwtException e) {
            throw new CustomJwtException(ErrorCode.JWT_ERROR);
        }
    }
}

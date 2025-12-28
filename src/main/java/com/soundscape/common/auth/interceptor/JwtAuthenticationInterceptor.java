package com.soundscape.common.auth.interceptor;

import com.soundscape.common.auth.context.UserContextHolder;
import com.soundscape.common.auth.jwt.JwtUtil;
import com.soundscape.common.auth.exception.CustomJwtException;
import com.soundscape.common.response.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 컨트롤러 메서드가 아니면 인터셉터의 검증 로직을 일단 적용 안함. (스웨거 경로)
        if (!(handler instanceof HandlerMethod)) return true;

        String token = extractToken(request);

        Jws<Claims> claims = jwtUtil.getClaims(token);
        String subject = claims.getPayload().getSubject();

        UserContextHolder.setUserContext(subject);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new CustomJwtException(ErrorCode.JWT_NOT_FOUND);
        }
        return header.substring(7);
    }
}

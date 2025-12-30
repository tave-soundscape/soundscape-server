package com.soundscape.user.service;

import com.soundscape.common.auth.jwt.JwtUtil;
import com.soundscape.user.api.dto.LoginResponseDto;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

//TODO RestClient 에러 핸들링 로직 추가 (우리 서비스 예외로 바꾸기)
@Service
@RequiredArgsConstructor
public class LoginService {

    private final RestClient restClient;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${kakao.client-id}") private String clientId;
    @Value("${kakao.redirect-uri}") private String redirectUri;
    @Value("${kakao.token-uri}") private String tokenUri;
    @Value("${kakao.user-info-uri}") private String userInfoUri;

    // TODO 추후 삭제: 서버 테스트용 카카오 로그인 페이지 URL 생성
    public String getLoginPage() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId +
                "&redirect_uri=" + redirectUri + "&response_type=code";
    }

    public LoginResponseDto login(String kakaoAccessToken) {
        String oid = getOauthId(kakaoAccessToken);
        User user = userRepository.findByOid(oid)
                .orElseGet(() -> {
                    User newUser = new User(oid);
                    return userRepository.save(newUser);
                });

        String accessToken = jwtUtil.createToken(user.getId().toString());
        return new LoginResponseDto(accessToken, null, user.isOnboarded());
    }

    private String getOauthId(String kakaoAccessToken) {
        Map<String, Object> kakaoUserInfo = restClient.get()
                .uri(userInfoUri)
                .header("Authorization",
                        "Bearer " + kakaoAccessToken).retrieve().body(Map.class
                );
        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUserInfo.get("kakao_account");
        String oid = kakaoUserInfo.get("id").toString();
        return oid;
    }

    // deprecated
    // TODO: 프론트에서 카카오 sdk 사용해서 추후 삭제
    public String requestAccessToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        Map<String, Object> response = restClient.post().uri(tokenUri).body(body).retrieve().body(Map.class);
        return (String) response.get("access_token");
    }
}

package com.soundscape.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundscape.dto.spotify.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SpotifyClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.code-verifier}")
    private String codeVerifier;

    public SpotifyTokenResponse exchangeCode(String code, String redirectUri) {
        String url = "https://accounts.spotify.com/api/token";

        Map<String, String> body = Map.of(
                "client_id", clientId,
                "grant_type", "authorization_code",
                "code", code,
                "redirect_uri", redirectUri,
                "code_verifier", codeVerifier
        );

        return restTemplate.postForObject(url, body, SpotifyTokenResponse.class);
    }

    public SpotifyProfile getUserProfile(String accessToken) {
        String url = "https://api.spotify.com/v1/me";

        var headers = new org.springframework.http.HttpHeaders();
        headers.setBearerAuth(accessToken);

        var entity = new org.springframework.http.HttpEntity<>(headers);

        var response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                SpotifyProfile.class
        );

        return response.getBody();
    }
}
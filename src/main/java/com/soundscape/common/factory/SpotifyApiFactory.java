package com.soundscape.common.factory;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.response.ErrorCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

@Component
public class SpotifyApiFactory {

    @Value("${spotify.client-id}")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;
    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    private SpotifyApi spotifyApi;
    private long expiresAt; // 토큰 만료 시간 기록 (Unix Timestamp)

    @PostConstruct
    public void init() {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        refreshAccessToken();
    }

    public SpotifyApi getSpotifyApi() {
        // 현재 시간보다 만료 시간이 적게 남았다면 (1분 전) 갱신
        if (System.currentTimeMillis() >= expiresAt - 60000) {
            refreshAccessToken();
        }
        return spotifyApi;
    }

    // TODO: 스케줄링 도입 고려
    private void refreshAccessToken() {
        try {
            var clientCredentialsRequest = spotifyApi.clientCredentials().build();
            var credentials = clientCredentialsRequest.execute();

            spotifyApi.setAccessToken(credentials.getAccessToken());
            // 현재 시간 + (유효기간 * 1000ms)로 만료 시점 계산
            this.expiresAt = System.currentTimeMillis() + (credentials.getExpiresIn() * 1000L);

        } catch (Exception e) {
            throw new BaseException("스포티파이 토큰 갱신 실패", ErrorCode.SPOTIFY_API_ERROR);
        }
    }
}

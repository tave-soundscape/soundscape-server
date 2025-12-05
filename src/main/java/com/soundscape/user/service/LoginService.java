package com.soundscape.user.service;

import com.soundscape.common.factory.SpotifyApiFactory;
import com.soundscape.common.response.ErrorCode;
import com.soundscape.user.domain.dto.LoginResponseDto;
import com.soundscape.user.exception.SpotifyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final SpotifyApiFactory spotifyApiFactory;

    // TODO: 추후 삭제 - 스포티파이 인증 코드 테스트용 메서드
    public String getLoginURI() {
        SpotifyApi spotifyApi = spotifyApiFactory.createSpotifyApi();

        List<String> scopes = new ArrayList<>();
        scopes.add("user-library-read");
        scopes.add("user-top-read");
        scopes.add("user-read-email");
        scopes.add("user-top-read");
        scopes.add("user-modify-playback-state");
        scopes.add("user-read-playback-state");

        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(String.join(" ", scopes))
                .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    public LoginResponseDto login(String userCode) {
        SpotifyApi spotifyApi = spotifyApiFactory.createSpotifyApi();
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(userCode).build();

        try {
            AuthorizationCodeCredentials credentials = authorizationCodeRequest.execute();

            String accessToken = credentials.getAccessToken();
            String refreshToken = credentials.getRefreshToken();

            return new LoginResponseDto(accessToken, refreshToken);

        } catch (Exception e) {
            throw new SpotifyException("Spotify 로그인 중 오류가 발생했습니다.", ErrorCode.SPOTIFY_API_ERROR);
        }
    }
}

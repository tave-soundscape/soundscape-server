package com.soundscape.common.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@Component
public class SpotifyApiFactory {

    @Value("${spotify.client-id}")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;
    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    public SpotifyApi createSpotifyApi() {

        URI redirectUriParam = URI.create(redirectUri);

        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUriParam)
                .build();
    }
}

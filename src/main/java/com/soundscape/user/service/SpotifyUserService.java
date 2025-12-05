package com.soundscape.user.service;

import com.soundscape.user.domain.entity.SpotifyUser;
import com.soundscape.user.repository.SpotifyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SpotifyUserService {

    private final SpotifyUserRepository spotifyUserRepository;

    public SpotifyUser insertOrUpdateSpotifyUser(User user, String accessToken, String refreshToken) {
        SpotifyUser spotifyUser = spotifyUserRepository.findBySpotifyId(user.getId());

        if (Objects.isNull(spotifyUser)) {
            spotifyUser = SpotifyUser.builder()
                    .spotifyId(user.getId())
                    .userName(user.getDisplayName())
                    .email(user.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        return spotifyUserRepository.save(spotifyUser);
    }
}

package com.soundscape.user.repository;

import com.soundscape.user.domain.entity.SpotifyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyUserRepository extends JpaRepository<SpotifyUser, Long> {
    SpotifyUser findBySpotifyId(String spotifyId);
}

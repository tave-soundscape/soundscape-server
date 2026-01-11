package com.soundscape.analytics.repository;

import com.soundscape.analytics.domain.SpotifyLinkClick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyLinkClickRepository extends JpaRepository<SpotifyLinkClick, Long> {
}

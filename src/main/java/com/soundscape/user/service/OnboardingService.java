package com.soundscape.user.service;


import com.soundscape.user.api.dto.OnboardingRequestDto;
import com.soundscape.user.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final UserReader userReader;

    @Transactional
    public void onboarding(Long userId, OnboardingRequestDto dto) {
        User user = userReader.getUser(userId);

        user.updateUsername(dto.getNickname());
        user.updateFavArtists(dto.getArtists());
        user.updateFavGenres(dto.getGenres());
    }
}

package com.soundscape.user.service;


import com.soundscape.user.api.dto.OnboardingRequestDto;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.exception.UserNotFoundException;
import com.soundscape.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final UserRepository userRepository;

    @Transactional
    public void onboarding(Long userId, OnboardingRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // 온보딩에서 받은 정보를 User에 넣을때도 그냥 Update로 넣어도 되는걸까요?
        // 초기 정보를 넣는건데 Update로 넣어도 되는건지 잘 모르겠습니다
        user.updateUsername(dto.getNickname());
        user.updateFavArtists(dto.getArtists());
        user.updateFavGenres(dto.getGenres());
    }
}

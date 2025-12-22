package com.soundscape.mypage.service;

import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;

    @Transactional
    public void updateName(Long userId, NameUpdateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        // 프론트에서 자체적으로 null 값으로 입력되는 경우 입력 안된다고 막는지 확인이 필요합니다
        // 만일 막는 경우 아래 코드 삭제 필요
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateUsername(request.getUsername());
    }
}

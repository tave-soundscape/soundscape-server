package com.soundscape.user.service;

import com.soundscape.user.domain.entity.User;
import com.soundscape.user.exception.UserNotFoundException;
import com.soundscape.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("userId: "+ userId + " - 존재하지 않는 회원입니다."));}
}

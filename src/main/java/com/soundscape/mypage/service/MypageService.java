package com.soundscape.mypage.service;

import com.soundscape.mypage.api.dto.FavArtistsUpdateRequestDto;
import com.soundscape.mypage.api.dto.FavGenresUpdateRequestDto;
import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.exception.UserNotFoundException;
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
                .orElseThrow(UserNotFoundException::new);

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateUsername(request.getUsername());
    }

    @Transactional
    public void updateFavArtists(Long userId, FavArtistsUpdateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (request.getArtists() == null || request.getArtists().isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateFavArtists(request.getArtists());
    }

    @Transactional
    public void updateFavGenres(Long userId, FavGenresUpdateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (request.getGenres() == null || request.getGenres().isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateFavGenres(request.getGenres());
    }
}

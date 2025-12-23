package com.soundscape.mypage.service;

import com.soundscape.mypage.api.dto.FavArtistsUpdateRequestDto;
import com.soundscape.mypage.api.dto.FavGenresUpdateRequestDto;
import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserReader userReader;

    @Transactional
    public void updateName(Long userId, NameUpdateRequestDto request) {
        User user = userReader.getUser(userId);

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateUsername(request.getUsername());
    }

    @Transactional
    public void updateFavArtists(Long userId, FavArtistsUpdateRequestDto request) {
        User user = userReader.getUser(userId);

        if (request.getArtists() == null || request.getArtists().isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateFavArtists(request.getArtists());
    }

    @Transactional
    public void updateFavGenres(Long userId, FavGenresUpdateRequestDto request) {
        User user = userReader.getUser(userId);

        if (request.getGenres() == null || request.getGenres().isEmpty()) {
            throw new IllegalArgumentException("username is required");
        }

        user.updateFavGenres(request.getGenres());
    }
}

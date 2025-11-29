package com.soundscape.service;

import com.soundscape.domain.User;
import com.soundscape.dto.*;
import com.soundscape.dto.spotify.SpotifyProfile;
import com.soundscape.dto.spotify.SpotifyTokenResponse;
import com.soundscape.exception.ResourceNotFoundException;
import com.soundscape.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final SpotifyClient spotifyClient;

    // 1) 마이페이지 조회
    @Transactional(readOnly = true)
    public MypageResponse getMypage(Long userId) {
        User user = findUserOrThrow(userId);

        return new MypageResponse(
                user.getId(),
                user.getName(),
                user.getSpotifyAccountId(),
                user.getSpotifyDisplayName(),
                List.copyOf(user.getFavArtists()),
                List.copyOf(user.getFavGenres()),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // 2) 이름 업데이트
    @Transactional
    public MypageResponse updateName(Long userId, String name) {
        User user = findUserOrThrow(userId);
        user.setName(name);
        return getMypage(userId); // 변경된 내용 다시 반환
    }

    // 3) 스포티파이 계정 연동
    @Transactional
    public MypageResponse updateSpotifyAccount(Long userId, String code, String redirectUri) {

        // 1) code + code_verifier 로 token 교환
        SpotifyTokenResponse token = spotifyClient.exchangeCode(code, redirectUri);

        // 2) 액세스토큰으로 프로필 가져오기
        SpotifyProfile profile = spotifyClient.getUserProfile(token.accessToken());

        // 3) DB 업데이트
        User user = findUserOrThrow(userId);

        user.setSpotifyAccountId(profile.id());
        user.setSpotifyDisplayName(profile.display_name());
        user.setRefreshToken(token.refreshToken());

        return getMypage(userId);
    }

    // 4) 아티스트 업데이트
    @Transactional
    public MypageResponse updateArtists(Long userId, FavoriteArtistsUpdateRequest request) {
        User user = findUserOrThrow(userId);

        user.getFavArtists().clear();
        user.getFavArtists().addAll(request.favArtists());

        return getMypage(userId);
    }

    // 5) 장르 업데이트
    @Transactional
    public MypageResponse updateGenres(Long userId, FavoriteGenresUpdateRequest request) {
        User user = findUserOrThrow(userId);

        user.getFavGenres().clear();
        user.getFavGenres().addAll(request.favGenres());

        return getMypage(userId);
    }

    // 공통 유저 조회
    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }
}
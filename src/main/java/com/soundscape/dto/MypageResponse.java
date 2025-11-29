package com.soundscape.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MypageResponse(
        Long userId,
        String name,
        String spotifyAccountId,
        String spotifyDisplayName, // 이 필드 필요한지 여부 확인 필요
        List<String> favArtists,
        List<String> favGenres,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
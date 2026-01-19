package com.soundscape.playlist.service.util;

import java.util.Map;

public final class PlaylistConstants {

    // 플레이리스트 설명
    public static final String DEFAULT_PLAYLIST_DESCRIPTION = "Soundscape AI가 추천한 상황 맞춤 음악";

    // 플레이리스트 이름 포맷
    public static final String PLAYLIST_NAME_FORMAT = "%s에서 듣기 좋은 플레이리스트, %s";

    // 날짜 포맷
    public static final String PLAYLIST_DATE_FORMAT = "yyyy-MM-dd HH:mm";

    // Location 한글 매핑
    private static final Map<String, String> LOCATION_KOREAN_MAP = Map.of(
            "gym", "헬스장",
            "cafe", "카페",
            "library", "도서관",
            "home", "집",
            "moving", "이동 중",
            "park", "공원",
            "co-working", "코워킹 스페이스"
    );

    public static String getKoreanLocation(String location) {
        return LOCATION_KOREAN_MAP.getOrDefault(location.toLowerCase(), location);
    }
}
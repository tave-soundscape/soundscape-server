package com.soundscape.playlist.infra.spotify;

import se.michaelthelin.spotify.model_objects.specification.Playlist;

public interface SpotifyPlaylistClient {
    // 스포티파이 플레이리스트 생성
    SpotifyPlaylistInfo createPlaylist(String userId, String playlistName);
    // 플레이리스트에 트랙 추가
    void addTracksToPlaylist(String playlistId, String[] trackUris);
    // 플레이리스트 상세 조회, 반환 타입은 스포티파이 라이브러리의 Playlist 객체
    Playlist getPlaylistDetails(String playlistId);
    // 플레이리스트 이름 변경
    void updatePlaylistName(String playlistId, String newName);
}

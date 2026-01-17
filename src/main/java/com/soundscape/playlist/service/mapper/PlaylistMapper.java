package com.soundscape.playlist.service.mapper;

import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.infra.engine.dto.EngineResponse;
import com.soundscape.playlist.service.util.DurationFormatter;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

public class PlaylistMapper {

    /**
     * EngineResponse의 TrackDto 리스트를 PlaylistResponse의 Song 리스트로 변환
     * @param tracks 엔진 응답의 트랙 리스트
     * @return Song DTO 리스트
     */
    public static List<PlaylistResponse.Song> toSongsFromEngine(List<EngineResponse.TrackDto> tracks) {
        return tracks.stream()
                .map(t -> PlaylistResponse.Song.builder()
                        .title(t.getTn())
                        .artistName(t.getAt().isEmpty() ? "Unknown" : t.getAt().get(0).getAtn())
                        .albumName(t.getAn())
                        .uri(t.getTu())
                        .spotifyUrl(t.getTurl())
                        .imageUrl(t.getImg())
                        .duration(DurationFormatter.format(t.getMs()))
                        .liked(false)
                        .build())
                .toList();
    }

    /**
     * Spotify Track 객체를 PlaylistResponse의 Song으로 변환
     * @param track Spotify Track 객체
     * @return Song DTO
     */
    public static PlaylistResponse.Song toSongFromSpotifyTrack(Track track) {
        return PlaylistResponse.Song.builder()
                .title(track.getName())
                .artistName(track.getArtists().length > 0 ? track.getArtists()[0].getName() : "Unknown")
                .albumName(track.getAlbum().getName())
                .uri(track.getUri())
                .spotifyUrl(track.getExternalUrls().get("spotify"))
                .imageUrl(track.getAlbum().getImages().length > 0 ? track.getAlbum().getImages()[0].getUrl() : null)
                .duration(DurationFormatter.format(track.getDurationMs()))
                .liked(false)
                .build();
    }
}


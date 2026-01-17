package com.soundscape.track.service;


import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.infra.spotify.SpotifyPlaylistClient;
import com.soundscape.playlist.infra.spotify.SpotifyPlaylistInfo;
import com.soundscape.playlist.service.mapper.PlaylistMapper;
import com.soundscape.track.domain.LikesSummaryResponse;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    @Value("${spotify.service-user-id}")
    private String serviceUserId;

    private final UserReader userReader;
    private final SpotifyPlaylistClient spotifyPlaylistClient;

    @Transactional
    public void likeTrack(Long userId, String trackUri) {
        User user = userReader.getUser(userId);

        // likes playlist 없으면 생성 후 저장
        if (user.getLikesSpotifyPlaylistId() == null || user.getLikesSpotifyPlaylistId().isBlank()) {
            String playlistName = (user.getUsername() == null || user.getUsername().isBlank())
                    ? "좋아요 한 노래"
                    : user.getUsername() + "의 좋아요 한 노래";

            SpotifyPlaylistInfo info = spotifyPlaylistClient.createPlaylist(serviceUserId, playlistName);
            user.updateLikesSpotifyPlaylistId(info.getPlaylistId());
        }

        spotifyPlaylistClient.addTracksToPlaylist(
                user.getLikesSpotifyPlaylistId(),
                new String[]{trackUri}
        );

        // count 증가
        user.increaseLikesCount();
    }

    @Transactional
    public void unlikeTrack(Long userId, String trackUri) {
        User user = userReader.getUser(userId);

        if (user.getLikesSpotifyPlaylistId() == null || user.getLikesSpotifyPlaylistId().isBlank()) {
            return; // 정책: 취소할 대상이 없음
        }

        spotifyPlaylistClient.removeTracksFromPlaylist(
                user.getLikesSpotifyPlaylistId(),
                new String[]{trackUri}
        );

        user.decreaseLikesCount();
    }

    @Transactional(readOnly = true)
    public LikesSummaryResponse getLikesSummary(Long userId) {
        User user = userReader.getUser(userId);

        if (user.getLikesSpotifyPlaylistId() == null || user.getLikesSpotifyPlaylistId().isBlank()) {
            return new LikesSummaryResponse(null, null, 0);
        }

        var playlist = spotifyPlaylistClient.getPlaylistDetails(user.getLikesSpotifyPlaylistId());
        String url = playlist.getExternalUrls().get("spotify");

        return new LikesSummaryResponse(
                user.getLikesSpotifyPlaylistId(),
                url,
                user.getLikesCount()
        );
    }

    @Transactional(readOnly = true)
    public PlaylistResponse getLikesPlaylist(Long userId) {
        User user = userReader.getUser(userId);

        if (user.getLikesSpotifyPlaylistId() == null || user.getLikesSpotifyPlaylistId().isBlank()) {
            return PlaylistResponse.builder()
                    .playlistId(null)
                    .playlistName("좋아요 한 노래")
                    .location(null)
                    .goal(null)
                    .spotifyPlaylistId(null)
                    .playlistUrl(null)
                    .songs(List.of())
                    .build();
        }

        var likesPlaylist = spotifyPlaylistClient.getPlaylistDetails(user.getLikesSpotifyPlaylistId());

        List<PlaylistResponse.Song> songs = Arrays.stream(likesPlaylist.getTracks().getItems())
                .map(item -> item.getTrack())
                .filter(t -> t instanceof Track)
                .map(t -> (Track) t)
                .map(track -> {
                    PlaylistResponse.Song base = PlaylistMapper.toSongFromSpotifyTrack(track);
                    return PlaylistResponse.Song.builder()
                            .title(base.getTitle())
                            .artistName(base.getArtistName())
                            .albumName(base.getAlbumName())
                            .uri(base.getUri())
                            .spotifyUrl(base.getSpotifyUrl())
                            .imageUrl(base.getImageUrl())
                            .duration(base.getDuration())
                            .liked(true) // 좋아요 플레이리스트 상세는 전부 true
                            .build();
                })
                .toList();

        return PlaylistResponse.builder()
                .playlistId(null)
                .playlistName("좋아요 한 노래")
                .location(null)
                .goal(null)
                .spotifyPlaylistId(user.getLikesSpotifyPlaylistId())
                .playlistUrl(likesPlaylist.getExternalUrls().get("spotify"))
                .songs(songs)
                .build();
    }
}

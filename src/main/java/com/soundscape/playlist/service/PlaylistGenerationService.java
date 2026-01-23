package com.soundscape.playlist.service;

import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.api.dto.response.PlaylistTaskStatusResponse;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.domain.PlaylistCondition;
import com.soundscape.playlist.domain.PlaylistGenerationTask;
import com.soundscape.playlist.infra.spotify.SpotifyPlaylistClient;
import com.soundscape.playlist.repository.PlaylistGenerationTaskRepository;
import com.soundscape.playlist.repository.PlaylistRepository;
import com.soundscape.playlist.service.command.PlaylistCommand;
import com.soundscape.playlist.service.mapper.PlaylistMapper;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistGenerationService {

    private final PlaylistGenerator playlistGenerator;
    private final SpotifyPlaylistClient spotifyPlaylistClient;
    private final PlaylistReader playlistReader;
    private final UserReader userReader;
    private final TaskReader taskReader;
    private final PlaylistRepository playlistRepository;
    private final PlaylistGenerationTaskRepository playlistGenerationTaskRepository;

    @Async("asyncExecutor")
    public void executeGeneration(Long userId, String taskId, PlaylistCommand command) {
        PlaylistGenerationTask task = taskReader.getTask(taskId);

        try {
            task.startTask();
            playlistGenerationTaskRepository.save(task);

            User user = userReader.getUser(userId);
            List<String> favArtists = user.getFavArtists();
            PlaylistResponse result = playlistGenerator.generate(command, favArtists);
            PlaylistCondition playlistCondition = new PlaylistCondition(command.getLocation(), command.getDecibel(), command.getGoal());
            Playlist playlist = new Playlist(
                    result.getPlaylistName(),
                    result.getPlaylistUrl(),
                    result.getSpotifyPlaylistId(),
                    playlistCondition
            );
            playlistRepository.save(playlist);

            task.completeTask(playlist.getId());
            playlistGenerationTaskRepository.save(task);
        } catch (Exception e) {
            log.error("플레이리스트 비동기 생성 에러 발생, 기본 플레이리스트(Id: 1) 반환");
            task.failTask();
            playlistGenerationTaskRepository.save(task);
            throw e;
        }
    }

    public PlaylistTaskStatusResponse getTaskStatus(String taskId, Long userId){
        PlaylistGenerationTask task = taskReader.getTaskWithUserId(taskId, userId);

        if (task.isFailed()) {
            log.error(taskId + ": 플레이리스트 비동기 생성 에러 발생하여, 기본 플레이리스트(Id: 1) 반환");
            Playlist playlist = playlistReader.getPlaylist(1L);
            String spotifyPlaylistId = playlist.getSpotifyPlaylistId();
            var spotifyPlaylistDetails = spotifyPlaylistClient.getPlaylistDetails(spotifyPlaylistId);

            List<PlaylistResponse.Song> songs = Arrays.stream(spotifyPlaylistDetails.getTracks().getItems())
                    .map(item -> {
                        var itemElement = item.getTrack();
                        if (itemElement instanceof Track track) {
                            return PlaylistMapper.toSongFromSpotifyTrack(track);
                        }
                        return null;
                    })
                    .filter(java.util.Objects::nonNull)
                    .toList();

            PlaylistResponse response = PlaylistResponse.builder()
                    .playlistId(playlist.getId())
                    .playlistName(playlist.getPlaylistName())
                    .location(playlist.getPlaylistCondition().getLocation())
                    .goal(playlist.getPlaylistCondition().getGoal())
                    .spotifyPlaylistId(spotifyPlaylistId)
                    .playlistUrl(playlist.getPlaylistUrl())
                    .songs(songs)
                    .build();

            return new PlaylistTaskStatusResponse(taskId, task.getStatus().name(), response);
        }

        else if (task.isCompleted()) {
            Playlist playlist = playlistReader.getPlaylist(task.getPlaylistId());
            String spotifyPlaylistId = playlist.getSpotifyPlaylistId();
            var spotifyPlaylistDetails = spotifyPlaylistClient.getPlaylistDetails(spotifyPlaylistId);

            List<PlaylistResponse.Song> songs = Arrays.stream(spotifyPlaylistDetails.getTracks().getItems())
                    .map(item -> {
                        var itemElement = item.getTrack();
                        if (itemElement instanceof Track track) {
                            return PlaylistMapper.toSongFromSpotifyTrack(track);
                        }
                        return null;
                    })
                    .filter(java.util.Objects::nonNull)
                    .toList();

            PlaylistResponse response = PlaylistResponse.builder()
                    .playlistId(playlist.getId())
                    .playlistName(playlist.getPlaylistName())
                    .location(playlist.getPlaylistCondition().getLocation())
                    .goal(playlist.getPlaylistCondition().getGoal())
                    .spotifyPlaylistId(spotifyPlaylistId)
                    .playlistUrl(playlist.getPlaylistUrl())
                    .songs(songs)
                    .build();

            return new PlaylistTaskStatusResponse(taskId, task.getStatus().name(), response);
        }

        return new PlaylistTaskStatusResponse(taskId, task.getStatus().name());
    }
}

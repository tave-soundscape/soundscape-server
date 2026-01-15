package com.soundscape.playlist.service;

import com.soundscape.playlist.domain.PlaylistGenerationTask;
import com.soundscape.playlist.exception.TaskNotFoundException;
import com.soundscape.playlist.repository.PlaylistGenerationTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskReader {

    private final PlaylistGenerationTaskRepository playlistGenerationTaskRepository;

    public PlaylistGenerationTask getTask(String taskId) {
        return playlistGenerationTaskRepository.findByTaskId(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Invalid task ID: " + taskId));
    }

    public PlaylistGenerationTask getTaskWithUserId(String taskId, Long userId) {
        return playlistGenerationTaskRepository.findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Invalid task ID or user ID: " + taskId + ", " + userId));
    }
}

package com.soundscape.playlist.repository;

import com.soundscape.playlist.domain.PlaylistGenerationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistGenerationTaskRepository extends JpaRepository<PlaylistGenerationTask, Long> {
    Optional<PlaylistGenerationTask> findByTaskId(String taskId);
    Optional<PlaylistGenerationTask> findByTaskIdAndUserId(String taskId, Long userId);
}

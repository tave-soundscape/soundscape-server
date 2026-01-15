package com.soundscape.playlist.domain;

import com.soundscape.common.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlist_generation_tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlaylistGenerationTask extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String taskId;

    private Long userId;
    private Long playlistId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    public PlaylistGenerationTask(String taskId, Long userId) {
        this.taskId = taskId;
        this.userId = userId;
        this.status = TaskStatus.PENDING;
    }

    public void startTask() {
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void failTask() {
        this.status = TaskStatus.FAILED;
    }

    public void completeTask(Long playlistId) {
        this.playlistId = playlistId;
        this.status = TaskStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return this.status == TaskStatus.COMPLETED;
    }

    public enum TaskStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }
}

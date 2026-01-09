package com.soundscape.playlist.repository;

import com.soundscape.playlist.domain.Playlist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT p FROM Playlist p " +
            "LEFT JOIN p.userPlaylists up " +
            "WHERE p.playlistCondition.location = :location " +
            "GROUP BY p " +
            "HAVING COUNT(up) > 0 OR p.createdAt >= :oneDayAgo " +
            "ORDER BY COUNT(up) DESC, p.createdAt DESC")
    Slice<Playlist> findByLocation(@Param("location") String location,
                                    @Param("oneDayAgo") LocalDateTime oneDayAgo,
                                    Pageable pageable);

    @Query("SELECT p FROM Playlist p " +
            "LEFT JOIN p.userPlaylists up " +
            "WHERE p.playlistCondition.goal = :goal " +
            "GROUP BY p " +
            "HAVING COUNT(up) > 0 OR p.createdAt >= :oneDayAgo " +
            "ORDER BY COUNT(up) DESC, p.createdAt DESC")
    Slice<Playlist> findByGoal(@Param("goal") String goal,
                                @Param("oneDayAgo") LocalDateTime oneDayAgo,
                                Pageable pageable);

    @Query("SELECT p FROM Playlist p " +
            "LEFT JOIN p.userPlaylists up " +
            "WHERE p.playlistCondition.decibel = :decibel " +
            "GROUP BY p " +
            "HAVING COUNT(up) > 0 OR p.createdAt >= :oneDayAgo " +
            "ORDER BY COUNT(up) DESC, p.createdAt DESC")
    Slice<Playlist> findByDecibel(@Param("decibel") String decibel,
                                   @Param("oneDayAgo") LocalDateTime oneDayAgo,
                                   Pageable pageable);

    // 플레이리스트의 저장 횟수 조회
    @Query("SELECT COUNT(up) FROM UserPlaylist up WHERE up.playlist.id = :playlistId")
    int countSavesByPlaylistId(@Param("playlistId") Long playlistId);
}

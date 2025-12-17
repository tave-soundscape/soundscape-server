package com.soundscape.playlist.repository;

import com.soundscape.playlist.domain.Playlist;
import com.soundscape.user.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByUser(User user);
    Slice<Playlist> findAllByUser(User user, Pageable pageable);
}

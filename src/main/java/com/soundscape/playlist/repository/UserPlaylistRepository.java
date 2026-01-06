package com.soundscape.playlist.repository;

import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.domain.UserPlaylist;
import com.soundscape.user.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPlaylistRepository extends JpaRepository<UserPlaylist, Long> {
    Slice<UserPlaylist> findAllByUser(User user, Pageable pageable);
    Optional<UserPlaylist> findByUserAndPlaylist(User user, Playlist playlist);
    Boolean existsByUserAndPlaylist(User user, Playlist playlist);
}

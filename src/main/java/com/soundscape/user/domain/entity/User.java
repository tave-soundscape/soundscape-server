package com.soundscape.user.domain.entity;

import com.soundscape.user.domain.converter.ListToStringConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Convert(converter = ListToStringConverter.class)
    private List<String> favArtists = new ArrayList<>();

    @Convert(converter = ListToStringConverter.class)
    private List<String> favGenres = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public void addFavArtist(String artist) {
        this.favArtists.add(artist);
    }
}

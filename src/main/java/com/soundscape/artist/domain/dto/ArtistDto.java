package com.soundscape.artist.domain.dto;

import com.soundscape.artist.domain.entity.Artist;
import lombok.Getter;

@Getter
public class ArtistDto {

    private final Long id;
    private final String name;
    private final String imageUrl;

    public ArtistDto(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.imageUrl = artist.getImageUrl();
    }
}

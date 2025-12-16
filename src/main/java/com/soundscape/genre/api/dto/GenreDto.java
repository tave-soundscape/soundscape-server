package com.soundscape.genre.api.dto;

import com.soundscape.genre.domain.entity.Genre;
import lombok.Getter;

@Getter
public class GenreDto {
    private final Long id;
    private final String genreName;

    public GenreDto(Genre genre){
        this.id = genre.getId();
        this.genreName = genre.getGenreName();
    }
}

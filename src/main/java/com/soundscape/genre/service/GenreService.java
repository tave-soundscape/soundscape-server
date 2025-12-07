package com.soundscape.genre.service;

import com.soundscape.genre.domain.dto.GenreDto;
import com.soundscape.genre.domain.entity.Genre;
import com.soundscape.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreDto> getAllGenres() {
        List<Genre> allGenres = genreRepository.findAll();
//        return allGenres.stream()
////                .map(GenreDto::new)
////                .toList();

        List<GenreDto> allGenreDto = new ArrayList<>();
        for (Genre genre: allGenres) {
            GenreDto genreDto = new GenreDto(genre);
            allGenreDto.add(genreDto);
        }
        return allGenreDto;
    }

}

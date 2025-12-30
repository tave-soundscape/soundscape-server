package com.soundscape.genre.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.genre.api.dto.GenreDto;
import com.soundscape.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController implements GenreDoc {
    private final GenreService genreService;

    @GetMapping
    public CommonResponse<List<GenreDto>> getAllGenres() {
        List<GenreDto> genres = genreService.getAllGenres();
        return CommonResponse.success(genres);
    }
}

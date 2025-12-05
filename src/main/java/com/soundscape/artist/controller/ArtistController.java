package com.soundscape.artist.controller;

import com.soundscape.artist.domain.dto.ArtistDto;
import com.soundscape.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    
    @GetMapping
    public List<ArtistDto> getAllArtists() {
        return artistService.getAllArtists();
    }
}

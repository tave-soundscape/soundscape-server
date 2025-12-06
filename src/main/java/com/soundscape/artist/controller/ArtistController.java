package com.soundscape.artist.controller;

import com.soundscape.artist.domain.dto.ArtistDto;
import com.soundscape.artist.service.ArtistService;
import com.soundscape.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController implements ArtistControllerDoc {

    private final ArtistService artistService;
    
    @GetMapping
    public CommonResponse getAllArtists() {
        List<ArtistDto> allArtists = artistService.getAllArtists();
        return CommonResponse.success(allArtists);
    }
}

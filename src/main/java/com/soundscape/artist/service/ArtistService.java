package com.soundscape.artist.service;

import com.soundscape.artist.domain.dto.ArtistDto;
import com.soundscape.artist.domain.entity.Artist;
import com.soundscape.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public List<ArtistDto> getAllArtists() {
        List<Artist> allArtists = artistRepository.findAll();
        return allArtists.stream()
                .map(ArtistDto::new)
                .toList();
    }
}

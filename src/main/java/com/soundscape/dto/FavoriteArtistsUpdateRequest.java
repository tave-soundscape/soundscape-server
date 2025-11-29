package com.soundscape.dto;

import jakarta.validation.constraints.Size;
import java.util.List;

public record FavoriteArtistsUpdateRequest(
        @Size(max = 3) List<String> favArtists
) {}
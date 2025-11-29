package com.soundscape.dto;

import jakarta.validation.constraints.NotBlank;

public record NameUpdateRequest(
        @NotBlank String name
) {}

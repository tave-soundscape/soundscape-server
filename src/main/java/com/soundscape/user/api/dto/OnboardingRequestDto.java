package com.soundscape.user.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OnboardingRequestDto {

    // final 이 필요한가요?
    private String nickname;
    private List<String> artists;
    private List<String> genres;
}

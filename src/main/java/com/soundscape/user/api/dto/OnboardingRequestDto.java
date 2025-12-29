package com.soundscape.user.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OnboardingRequestDto {
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 20, message = "닉네임은 20자 이내여야 합니다.")
    @Pattern(
            regexp = "^[A-Za-z가-힣]+$",
            message = "닉네임은 한글 또는 영문만 사용할 수 있습니다."
    )
    private String nickname;

    private List<String> artists;
    private List<String> genres;
}

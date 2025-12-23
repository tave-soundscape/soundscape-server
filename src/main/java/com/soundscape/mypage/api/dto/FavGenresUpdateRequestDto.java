package com.soundscape.mypage.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavGenresUpdateRequestDto {

    private List<String> genres;
}

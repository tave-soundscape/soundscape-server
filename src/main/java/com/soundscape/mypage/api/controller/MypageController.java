package com.soundscape.mypage.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import com.soundscape.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @PatchMapping("/update/name")
    public CommonResponse updateName(@RequestParam Long userId, @RequestBody NameUpdateRequestDto dto) {
        mypageService.updateName(userId, dto);
        // 리턴할 때 뭘 넣어야할지 모르겠어서 요청으로 들어온 이름으로 넣었는데 괜찮을까요?
        return CommonResponse.success(dto.getUsername());
    }
}

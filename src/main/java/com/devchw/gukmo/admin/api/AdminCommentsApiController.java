package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.service.AdminCommentsService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.devchw.gukmo.config.response.BaseResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class AdminCommentsApiController {
    private final AdminCommentsService adminCommentsService;

    @PatchMapping("/{id}/blind")
    public BaseResponse<String> blind(@PathVariable Long id) {
        Long blindId = adminCommentsService.blind(id);
        return new BaseResponse<>(SUCCESS);
    }

    @PatchMapping("/{id}/blind/clear")
    public BaseResponse<String> clearBlind(@PathVariable Long id) {
        Long blindId = adminCommentsService.blind(id);
        return new BaseResponse<>(SUCCESS);
    }
}

package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.service.CommentsService;
import com.devchw.gukmo.config.response.BaseResponse;
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
@RequestMapping("/api/v1/admin/comments")
public class CommentsApiController {
    private final CommentsService adminCommentsService;

    /** 댓글 블라인드 시키기 */
    @PatchMapping("/{id}/blind")
    public BaseResponse<String> blind(@PathVariable Long id) {
        Long blindId = adminCommentsService.blind(id);
        return new BaseResponse<>(SUCCESS);
    }

    /** 댓글 블라인드 해제 */
    @PatchMapping("/{id}/blind/clear")
    public BaseResponse<String> clearBlind(@PathVariable Long id) {
        Long blindId = adminCommentsService.clearBlind(id);
        return new BaseResponse<>(SUCCESS);
    }
}

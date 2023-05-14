package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.api.like.LikeResponse;
import com.devchw.gukmo.user.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;

    /** 게시글에서 추천 클릭시 처리 API */
    @GetMapping("/board")
    public BaseResponse<LikeResponse> boardLike(@RequestParam Long memberId, @RequestParam Long boardId) {
        LikeResponse likeResponse = new LikeResponse(likeService.boardLike(memberId, boardId));
        return new BaseResponse<>(likeResponse);
    }

    /** 댓글에서 추천 클릭시 처리 API */
    @GetMapping("/comments")
    public BaseResponse<LikeResponse> commentLike(@RequestParam Long memberId, @RequestParam Long commentId) {
        LikeResponse likeResponse = new LikeResponse(likeService.commentLike(memberId, commentId));
        return new BaseResponse<>(likeResponse);
    }
}

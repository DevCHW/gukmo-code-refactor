package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.api.like.LikeResponse;
import com.devchw.gukmo.user.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService;

    /** 게시글에서 추천 클릭시 처리 API */
    @PostMapping("/board")
    public BaseResponse<LikeResponse> boardLike(@RequestParam Long memberId, @RequestParam Long boardId) {
        String deleteOrInsert = likeService.boardLike(memberId, boardId);
        LikeResponse likeResponse = LikeResponse.builder()
                .deleteOrInsert(deleteOrInsert)
                .build();
        return new BaseResponse<>(likeResponse);
    }

    /** 댓글에서 추천 클릭시 처리 API */
    @PostMapping("/comments")
    public BaseResponse<LikeResponse> commentLike(@RequestParam Long memberId, @RequestParam Long commentId) {
        String deleteOrInsert = likeService.commentLike(memberId, commentId);
        LikeResponse likeResponse = LikeResponse.builder()
                .deleteOrInsert(deleteOrInsert)
                .build();
        return new BaseResponse<>(likeResponse);
    }
}

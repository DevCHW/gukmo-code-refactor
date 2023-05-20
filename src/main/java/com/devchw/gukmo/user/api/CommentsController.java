package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.comments.patch.UpdateRequest;
import com.devchw.gukmo.user.dto.api.comments.post.CommentsPostRequest;
import com.devchw.gukmo.user.service.CommentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentsController {
    private final CommentsService commentsService;

    /** 댓글 삭제 */
    @DeleteMapping("/{id}")
    public BaseResponse<String> delete(@PathVariable("id") Long id) {
        commentsService.delete(id);
        return new BaseResponse<>(SUCCESS);
    }


    /** 댓글 수정 */
    @PatchMapping("/{id}")
    public BaseResponse<String> edit(@PathVariable("id") Long id,
                                     @ModelAttribute UpdateRequest request) {
        log.info("날아온 데이터={}", request);
        commentsService.edit(id, request);
        return new BaseResponse<>(SUCCESS);
    }

    /** 댓글 작성 */
    @PostMapping
    public BaseResponse<String> save(CommentsPostRequest request) {
        if(commentsService.save(request) == null) {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }

        return new BaseResponse<>(SUCCESS);
    }
}

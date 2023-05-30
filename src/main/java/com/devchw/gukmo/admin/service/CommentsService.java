package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.repository.AdminCommentsRepository;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsService {
    private final AdminCommentsRepository adminCommentsRepository;

    /** 댓글 블라인드 처리하기 */
    @Transactional
    public Long blind(Long id) {
        Comments comments = adminCommentsRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_COMMENT));
        comments.blind();
        return comments.getId();
    }

    /** 댓글 블라인드 해제하기 */
    @Transactional
    public Long clearBlind(Long id) {
        Comments comments = adminCommentsRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_COMMENT));
        comments.clearBlind();
        return comments.getId();
    }
}

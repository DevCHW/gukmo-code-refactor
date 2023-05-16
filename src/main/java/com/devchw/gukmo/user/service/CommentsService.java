package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.comments.UpdateRequest;
import com.devchw.gukmo.user.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {

    private final CommentsRepository commentsRepository;

    /** 댓글 삭제 */
    @Transactional
    public void delete(Long id) {
        commentsRepository.deleteById(id);
    }

    /** 댓글 수정 */
    @Transactional
    public void edit(Long id, UpdateRequest request) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_COMMENT));
        comments.changeContent(request.getContent());
    }
}

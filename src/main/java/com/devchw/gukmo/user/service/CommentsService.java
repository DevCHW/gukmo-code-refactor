package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.comments.patch.UpdateRequest;
import com.devchw.gukmo.user.dto.api.comments.post.CommentsPostRequest;
import com.devchw.gukmo.user.repository.ActivityRepository;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.repository.CommentsRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;
import static com.devchw.gukmo.entity.member.Activity.Division.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {
    private final BoardRepository boardRepository;
    private final CommentsRepository commentsRepository;
    private final MemberRepository memberRepository;
    private final ActivityRepository activityRepository;

    /** 댓글 삭제 */
    @Transactional
    public void delete(Long id) {
        Comments comments = commentsRepository.findCommentsWithMemberWithBoardById(id).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
        Board board = comments.getBoard();
        Long childSize = commentsRepository.countByParentId(comments.getId());
        board.commentMinus(1 + childSize);

        commentsRepository.deleteById(id);

        //포인트 감소
        comments.getMember().pointMinus(10);
    }

    /** 댓글 수정 */
    @Transactional
    public void edit(Long id, UpdateRequest request) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
        comments.changeContent(request.getContent());
    }

    /** 댓글 작성 */
    @Transactional
    public Comments save(CommentsPostRequest request) {
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));

        //댓글갯수 증가
        board.commentPlus();

        //활동점수 올려주기
        member.pointPlus(10);

        // 부모댓글이 있는지 확인
        Comments parent = request.getParentId() != null?
                commentsRepository.findById(request.getParentId()).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT)):null;

        Comments comments = request.toEntity(board, member, parent);
        Comments saveComments = commentsRepository.save(comments);

        //활동내역 넣어주기
        Activity activity = Activity.builder()
                .member(member)
                .board(board)
                .comments(saveComments)
                .division(COMMENT_WRITE)
                .build();
        Activity saveActivity = activityRepository.save(activity);

        return saveComments;
    }
}

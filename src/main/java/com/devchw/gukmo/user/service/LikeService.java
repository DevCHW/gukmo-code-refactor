package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardLike;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.comment.CommentsLike;
import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;
import static com.devchw.gukmo.entity.member.Activity.Division.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final CommentsLikeRepository commentsLikeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentsRepository commentsRepository;
    private final ActivityRepository activityRepository;

    /** 게시물 추천 */
    @Transactional
    public String boardLike(Long memberId, Long boardId) {
        Boolean exist = boardLikeRepository.existsByBoardIdAndMemberId(boardId, memberId);

        if(exist) { // 추천 삭제
            BoardLike boardLike = boardLikeRepository.findByBoardIdAndMemberId(boardId, memberId).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD_LIKE));
            System.out.println("여기서 한번더 쿼리가 나가나?");
            Board board = boardLike.getBoard();
            boardLikeRepository.deleteById(boardLike.getId());

            //활동내역 삭제
            Long activityId = activityRepository.findByMemberIdAndBoardIdAndDivision(memberId, boardId, BOARD_LIKE).getId();
            activityRepository.deleteById(activityId);

            board.likeMinus();
            return "delete";
        } else { //추천 생성
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
            BoardLike boardLike = BoardLike.builder()
                    .member(member)
                    .board(board)
                    .build();
            boardLikeRepository.save(boardLike).getId();

            //활동내역 넣어주기
            Activity activity = Activity.builder()
                    .member(member)
                    .board(board)
                    .division(BOARD_LIKE)
                    .build();

            Activity saveActivity = activityRepository.save(activity);

            board.likePlus();
            return "insert";
        }
    }

    /** 댓글 추천 */
    @Transactional
    public String commentLike(Long memberId, Long commentsId) {
        Boolean exist = commentsLikeRepository.existsByCommentsIdAndMemberId(commentsId, memberId);
        if(exist) { // 추천 삭제

            Long id = commentsLikeRepository.findByCommentsIdAndMemberId(commentsId, memberId).getId();
            Comments comments = commentsRepository.findWithBoardById(commentsId).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
            commentsLikeRepository.deleteById(id);

            //활동내역 삭제
            Long activityId = activityRepository.findByMemberIdAndCommentsIdAndDivision(memberId, commentsId, COMMENT_LIKE).orElseThrow(() -> new BaseException(NOT_FOUND_ACTIVITY)).getId();
            activityRepository.deleteById(activityId);

            comments.likeMinus();
            return "delete";

        } else { // 추천 생성
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
            Comments comments = commentsRepository.findWithBoardById(commentsId).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
            CommentsLike commentsLike = CommentsLike.builder()
                    .member(member)
                    .comments(comments)
                    .build();
            Long id = commentsLikeRepository.save(commentsLike).getId();

            //활동내역 넣어주기
            Activity activity = Activity.builder()
                    .member(member)
                    .board(comments.getBoard())
                    .comments(comments)
                    .division(COMMENT_LIKE)
                    .build();

            Activity saveActivity = activityRepository.save(activity);

            comments.likePlus();
            if(id == null) throw new BaseException(BAD_REQUEST);
            return "insert";
        }
    }
}

package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardLike;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.comment.CommentsLike;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

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

    @Transactional
    public String boardLike(Long memberId, Long boardId) {
        Boolean exist = boardLikeRepository.existsByBoardIdAndMemberId(boardId, memberId);
        if(exist) { // 삭제
            Long id = boardLikeRepository.deleteByBoardIdAndMemberId(boardId, memberId);
            return "delete";
        } else { //생성
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

            BoardLike boardLike = BoardLike.builder()
                    .member(member)
                    .board(board)
                    .build();
            Long id = boardLikeRepository.save(boardLike).getId();
            return "insert";
        }
    }

    public String commentLike(Long memberId, Long commentId) {
        Boolean exist = commentsLikeRepository.existsByBoardIdAndMemberId(commentId, memberId);
        if(exist) { // 삭제
            Long id = commentsLikeRepository.deleteByBoardIdAndMemberId(commentId, memberId);
            return "delete";
        } else { //생성
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
            Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
            CommentsLike commentsLike = CommentsLike.builder()
                    .member(member)
                    .comments(comments)
                    .build();
            Long id = commentsLikeRepository.save(commentsLike).getId();
            return "insert";
        }
    }
}

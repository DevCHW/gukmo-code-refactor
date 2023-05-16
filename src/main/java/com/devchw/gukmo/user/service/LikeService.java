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
            Long id = boardLikeRepository.findByBoardIdAndMemberId(boardId, memberId).getId();
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
            boardLikeRepository.deleteById(id);
            board.likeMinus();
            return "delete";
        } else { //생성
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
            Board board = boardRepository.findById(boardId).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
            BoardLike boardLike = BoardLike.builder()
                    .member(member)
                    .board(board)
                    .build();
            boardLikeRepository.save(boardLike).getId();
            board.likePlus();
            return "insert";
        }
    }

    @Transactional
    public String commentLike(Long memberId, Long commentsId) {
        Boolean exist = commentsLikeRepository.existsByCommentsIdAndMemberId(commentsId, memberId);
        if(exist) { // 삭제

            Long id = commentsLikeRepository.findByCommentsIdAndMemberId(commentsId, memberId).getId();
            Comments comments = commentsRepository.findById(commentsId).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
            commentsLikeRepository.deleteById(id);
            comments.likeMinus();
            return "delete";

        } else { //생성
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
            Comments comments = commentsRepository.findById(commentsId).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
            CommentsLike commentsLike = CommentsLike.builder()
                    .member(member)
                    .comments(comments)
                    .build();
            Long id = commentsLikeRepository.save(commentsLike).getId();
            comments.likePlus();
            if(id == null) throw new BaseException(BAD_REQUEST);
            return "insert";
        }
    }
}

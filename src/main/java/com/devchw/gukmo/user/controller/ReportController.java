package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.repository.CommentsRepository;
import com.devchw.gukmo.user.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_BOARD;
import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_COMMENT;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final BoardRepository boardRepository;
    private final CommentsRepository commentsRepository;

    /** 게시물 신고 폼 */
    @GetMapping("/boards/{id}")
    public String boardReport(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.findWithMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        model.addAttribute("boardId", board.getId());
        return "/report/report";
    }

    /** 댓글 신고 폼 */
    @GetMapping("/comments/{id}")
    public String commentReport(@PathVariable("id") Long id, Model model) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
        model.addAttribute("commentId", comments.getId());
        return "/report/report";
    }
}

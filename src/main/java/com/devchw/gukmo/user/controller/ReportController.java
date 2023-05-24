package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_BOARD;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final BoardRepository boardRepository;
    @GetMapping("/boards/{id}")
    public String boardReport(@PathVariable("id") Long id) {
        Board board = boardRepository.findWithMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));

        return "/report/report";
    }

    @GetMapping("/comment/{id}")
    public String commentReport(@PathVariable("id") Long id) {
        return "/report/report";
    }
}

package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.dto.BoardDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

//    /**
//     * 게시글 리스트 조회(페이징)
//     */
//    @GetMapping("/boards/category/{category_id}")
//    public String boards(@PathVariable Long categoryId,
//                         @RequestParam String sort,
//                         @RequestParam String keyword,
//                         @RequestParam ) {
//    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/board/{boardId}")
    public String board(@PathVariable Long boardId, Model model) {
        log.info("게시글 단건 조회 요청");
        Optional<Board> board = boardRepository.findById(boardId);
        return "board/board.tiles1";
    }
}

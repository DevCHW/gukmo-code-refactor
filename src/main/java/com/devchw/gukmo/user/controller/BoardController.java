package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    /**
     * 게시글 리스트 조회(페이징)
     */
    @GetMapping("/boards")
    public String boards(@RequestParam("category") List<String> categories,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "sort", required = false) String sort,
                         Pageable pageable,
                         Model model) {
        model.addAttribute("category", categories.get(categories.size()-1));
        boardRepository.findBoardList(categories, keyword, sort, pageable);
        return "board/communityList.tiles1";
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/board/{id}")
    public String board(@PathVariable Long id, Model model) {
        log.info("게시글 단건 조회 요청");
        Optional<Board> board = boardRepository.findById(id);
        log.info("조회된 게시글 정보 ={}", board);
        return "board/board.tiles1";
    }
}

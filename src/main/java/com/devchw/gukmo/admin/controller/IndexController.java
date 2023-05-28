package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.dto.board.BoardDto;
import com.devchw.gukmo.admin.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final IndexService indexService;

    @GetMapping("/admin")
    public String index(Model model) {
        //인기게시물 3개 가져오기
        List<BoardDto> popularBoardList = indexService.findPopularBoardList();
        model.addAttribute("popularBoardList", popularBoardList);

        //통계
        Map<String, Long> summaryMap = indexService.findSummaryCount();

        model.addAttribute("summaryMap", summaryMap);
        return "index.tiles2";
    }
}

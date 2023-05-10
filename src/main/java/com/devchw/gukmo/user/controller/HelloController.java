package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.dto.board.get.IndexBoardDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HelloController {

    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String hello(Model model) {
        String[] secondCategory = {"자유", "QnA", "스터디", "취미모임", "수강/취업후기"};
        //뷰단에서 찍을 모델명 규약
        String[] modelNameArr = {"free", "QnA", "study", "hobby", "review"};
        for(int i=0; i<5; i++) {
            List<Board> board = boardRepository.findTop5BoardBySecondCategoryOrderByIdDesc(secondCategory[i]);
            List<IndexBoardDto> indexBoardModelList = new ArrayList<>();
            for(int j=0; j<board.size(); j++) {
                IndexBoardDto indexBoardDto = new IndexBoardDto().toDto(board.get(j));
                indexBoardModelList.add(indexBoardDto);
            }
            model.addAttribute(modelNameArr[i], indexBoardModelList);
        }

        return "index.tiles1";
    }
}

package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Curriculum;
import com.devchw.gukmo.user.dto.advertisement.AdvertisementDto;
import com.devchw.gukmo.user.dto.board.IndexBoardDto;
import com.devchw.gukmo.user.dto.board.IndexCurriculumListDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.AdvertisementService;
import com.devchw.gukmo.user.service.HashtagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HelloController {

    private final BoardRepository boardRepository;
    private final AdvertisementService advertisementService;
    private final HashtagService hashtagService;

    @GetMapping("/")
    public String hello(Model model) {
        // 조건에 맞는 메인페이지 광고 5개 조회
        LocalDateTime currentDateTime = LocalDateTime.now();
        Set<Advertisement> advertisements = advertisementService.mainAdvertisement(currentDateTime);
        List<AdvertisementDto> advertisementList = advertisements.stream().map(a -> new AdvertisementDto().toDto(a)).collect(Collectors.toList());
        log.info("조회된 광고 5개={}", advertisementList);
        model.addAttribute("advertisementList", advertisementList);

        //현재 모집중인 과정 조회
        List<Curriculum> curricula = boardRepository.findTop24ByRecruitmentStartDateLessThanAndRecruitmentEndDateGreaterThanOrderByIdDesc(currentDateTime, currentDateTime);
        List<IndexCurriculumListDto> curriculumList = curricula.stream().map(c -> new IndexCurriculumListDto().toDto(c)).collect(Collectors.toList());
        log.info("현재 모집중인 과정={}", curriculumList);
        List<IndexCurriculumListDto> curriculumList1 = new ArrayList<>();
        List<IndexCurriculumListDto> curriculumList2 = new ArrayList<>();
        List<IndexCurriculumListDto> curriculumList3 = new ArrayList<>();

        //인기 해시태그 조회
        List<String> bestHashtags = hashtagService.getTop10BestHashtag();
        log.info("조회된 인기 해시태그 10={}", bestHashtags);
        model.addAttribute("bestHashtags", bestHashtags);

        int count =1;
        for (IndexCurriculumListDto indexCurriculumListDto : curriculumList) {
            if(count <= 8) {
                curriculumList1.add(indexCurriculumListDto);
            } else if(count <= 16) {
                curriculumList2.add(indexCurriculumListDto);
            } else {
                curriculumList3.add(indexCurriculumListDto);
            }
            count++;
        }

        model.addAttribute("curriculumList", curriculumList);
        model.addAttribute("curriculumList1", curriculumList1);
        model.addAttribute("curriculumList2", curriculumList2);
        model.addAttribute("curriculumList3", curriculumList3);

        //뷰단에서 카테고리 모델명 규약
        String[] secondCategory = {"자유", "QnA", "스터디", "취미모임", "수강/취업후기"};
        String[] modelNameArr = {"free", "QnA", "study", "hobby", "review"};
        for(int i=0; i<5; i++) { //각 카테고리별 게시글 데이터 5개씩 조회하여 데이터 전송.
            List<Board> board = boardRepository.findTop5BoardBySecondCategoryOrderByIdDesc(secondCategory[i]);
            List<IndexBoardDto> indexBoardModelList = new ArrayList<>();
            for(int j=0; j<board.size(); j++) {
                IndexBoardDto indexBoardDto = new IndexBoardDto().toDto(board.get(j));
                indexBoardModelList.add(indexBoardDto);
                log.info("조회한 게시물 5개={}", indexBoardDto);
            }
            model.addAttribute(modelNameArr[i], indexBoardModelList);
        }

        return "index.tiles1";
    }
}

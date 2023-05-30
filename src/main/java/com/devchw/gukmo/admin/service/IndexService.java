package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.board.BoardDto;
import com.devchw.gukmo.admin.repository.AdminBoardRepository;
import com.devchw.gukmo.admin.repository.AdminMemberRepository;
import com.devchw.gukmo.entity.board.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndexService {

    private final AdminBoardRepository adminBoardRepository;
    private final AdminMemberRepository adminMemberRepository;
    public List<BoardDto> findPopularBoardList() {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59

        String firstCategory = "커뮤니티";
        List<Board> findBoardList = adminBoardRepository.findTop3ByFirstCategoryAndWriteDateBetweenOrderByViewsDesc(firstCategory, startDatetime, endDatetime);
        return findBoardList.stream().map(b -> new BoardDto().toDto(b)).collect(Collectors.toList());
    }

    public Map<String, Long> findSummaryCount() {
        Map<String, Long> map = new ConcurrentHashMap<>();
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59

        //오늘 작성된 게시물 수
        Long todayBoardCount = adminBoardRepository.countByWriteDateBetween(startDatetime, endDatetime);

        //오늘 가입한 회원 수
        Long todayJoinMemberCount = adminMemberRepository.countByJoinDateBetween(startDatetime, endDatetime);

        //회원가입이 가장 많았던 날의 회원가입 수
        Long joinMemberCountMax = adminMemberRepository.countByJoinMemberMax();

        //작성된 게시물이 가장 많았던 날의 게시물 수
        Long writeBoardCountMax = adminBoardRepository.countByWriteBoardCountMax();

        double maxCntBoard = (double) writeBoardCountMax;
        double maxCntJoinMember = (double) joinMemberCountMax;

        Long boardPercentage = (long) ((todayBoardCount / maxCntBoard) * 100) > 100 ? 100 : (long) ((todayBoardCount / maxCntBoard) * 100);
        Long memberPercentage = (long) ((todayJoinMemberCount / maxCntJoinMember) * 100) > 100 ? 100 : (long) ((todayJoinMemberCount / maxCntJoinMember) * 100);

        map.put("boardPercentage", boardPercentage);
        map.put("memberPercentage", memberPercentage);
        map.put("todayBoardCount", todayBoardCount);
        map.put("todayJoinMemberCount", todayJoinMemberCount);
        map.put("joinMemberCountMax", joinMemberCountMax);
        map.put("writeBoardCountMax", writeBoardCountMax);
        return map;
    }
}

package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.board.CommunityActivityStatsQueryDto;
import com.devchw.gukmo.admin.repository.AdminBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBoardService {
    private final AdminBoardRepository adminBoardRepository;

    /** 오늘자 커뮤니티 활성비율 통계 얻기 */
    public List<Long> findCommunityActivityStats() {
        CommunityActivityStatsQueryDto findData = adminBoardRepository.findCommunityActivityStats();
        List<Long> data = new ArrayList<>();
        data.add(findData.getFreePercentage());
        data.add(findData.getQnaPercentage());
        data.add(findData.getStudyPercentage());
        data.add(findData.getHobbyPercentage());
        data.add(findData.getReviewPercentage());
        return data;
    }
}

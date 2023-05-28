package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.api.board.CommunityActivityStatsResponse;
import com.devchw.gukmo.admin.dto.api.member.IncreaseStatsResponse;
import com.devchw.gukmo.admin.service.AdminBoardService;
import com.devchw.gukmo.config.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/boards")
public class AdminBoardApiController {

    private final AdminBoardService adminBoardService;

    /** 오늘자 커뮤니티 활성비율 통계 얻기 */
    @GetMapping("/community/activity/stats")
    public BaseResponse<CommunityActivityStatsResponse> communityActivityStats() {
        List<Long> findData = adminBoardService.findCommunityActivityStats();
        log.info("조회된 데이터={}", findData);
        CommunityActivityStatsResponse data = CommunityActivityStatsResponse.builder()
                .data(findData)
                .build();
        return new BaseResponse<>(data);
    }
}

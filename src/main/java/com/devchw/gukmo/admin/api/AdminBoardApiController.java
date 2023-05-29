package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.api.board.CommunityActivityStatsResponse;
import com.devchw.gukmo.admin.dto.api.member.IncreaseStatsResponse;
import com.devchw.gukmo.admin.service.AdminBoardService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

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
        CommunityActivityStatsResponse data = CommunityActivityStatsResponse.builder()
                .data(findData)
                .build();
        return new BaseResponse<>(data);
    }
}

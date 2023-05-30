package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.board.CommunityActivityStatsResponse;
import com.devchw.gukmo.admin.dto.api.member.IncreaseStatsResponse;
import com.devchw.gukmo.admin.dto.board.BoardListDto;
import com.devchw.gukmo.admin.service.AdminBoardService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
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

    /** 회원이 작성한 작성게시물 리스트 조회 */
    @PostMapping("/members/{id}")
    public DataTableResponse boards(@PathVariable Long id, @RequestBody MultiValueMap<String, String> formData) {
        int draw = Integer.parseInt(formData.get("draw").get(0));
        int start = Integer.parseInt(formData.get("start").get(0));
        int length = Integer.parseInt(formData.get("length").get(0));

        List<BoardListDto> findData = adminBoardService.findAllBoardList(start, length, formData, id);
        System.out.println("findData.size() = " + findData.size());
        int total = (int) adminBoardService.findAllBoardListTotal(id);
        DataTableResponse data = DataTableResponse.builder()
                .draw(draw)
                .recordsFiltered(total)
                .recordsTotal(total)
                .data(findData)
                .build();
        return data;
    }
}

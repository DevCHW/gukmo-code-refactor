package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.api.member.IncreaseStatsResponse;
import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.service.MemberService;
import com.devchw.gukmo.config.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
public class MemberApiController {
    private final MemberService adminMemberService;

    /** 관리자 사이트 이용자 수 증가 통계 */
    @GetMapping("/increase/stats")
    public BaseResponse<IncreaseStatsResponse> IncreaseStats() {
        List<Long> findData = adminMemberService.findIncreaseStats();
        IncreaseStatsResponse data = IncreaseStatsResponse.builder()
                .data(findData)
                .build();
        return new BaseResponse<>(data);
    }

    /** 관리자 회원내역 조회 */
    @PostMapping
    public DataTableResponse members(@RequestBody MultiValueMap<String, String> formData) {
        int draw = Integer.parseInt(formData.get("draw").get(0));
        int start = Integer.parseInt(formData.get("start").get(0));
        int length = Integer.parseInt(formData.get("length").get(0));

        List<MemberListDto> findData = adminMemberService.findAllMemberList(start, length, formData);
        int total = (int) adminMemberService.findAllMemberListTotal(formData);

        DataTableResponse data = DataTableResponse.builder()
                .draw(draw)
                .recordsFiltered(total)
                .recordsTotal(total)
                .data(findData)
                .build();
        return data;
    }
}

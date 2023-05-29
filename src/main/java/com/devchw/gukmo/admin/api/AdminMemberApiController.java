package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.api.member.IncreaseStatsResponse;
import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.service.AdminMemberService;
import com.devchw.gukmo.config.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
public class AdminMemberApiController {
    private final AdminMemberService adminMemberService;

    /** 관리자 사이트 이용자 수 증가 통계 */
    @GetMapping("/increase/stats")
    public BaseResponse<IncreaseStatsResponse> IncreaseStats() {
        List<Long> findData = adminMemberService.findIncreaseStats();
        log.info("조회된 데이터={}", findData);
        IncreaseStatsResponse data = IncreaseStatsResponse.builder()
                .data(findData)
                .build();
        return new BaseResponse<>(data);
    }

    /** 관리자 회원내역 조회 */
    @PostMapping
    public BaseResponse<DataTableResponse> members(@RequestBody MultiValueMap<String, String> formData) {
        int draw = Integer.parseInt(formData.get("draw").get(0));
        int start = Integer.parseInt(formData.get("start").get(0));
        int length = Integer.parseInt(formData.get("length").get(0));

        log.info("formData={}", formData);
        List<MemberListDto> findData = adminMemberService.findAllMemberList(start, length, formData);
        DataTableResponse data = DataTableResponse.builder()
                .build();
        return new BaseResponse<>(data);
    }
}

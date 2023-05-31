package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.api.member.IncreaseStatsResponse;
import com.devchw.gukmo.admin.dto.api.member.MemberInfoRequest;
import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.service.MemberService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devchw.gukmo.config.response.BaseResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
public class MemberApiController {
    private final MemberService memberService;


    /** 관리자 사이트 이용자 수 증가 통계 */
    @GetMapping("/increase/stats")
    public BaseResponse<IncreaseStatsResponse> IncreaseStats() {
        List<Long> findData = memberService.findIncreaseStats();
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

        List<MemberListDto> findData = memberService.findAllMemberList(start, length, formData);
        int total = (int) memberService.findAllMemberListTotal(formData);

        DataTableResponse data = DataTableResponse.builder()
                .draw(draw)
                .recordsFiltered(total)
                .recordsTotal(total)
                .data(findData)
                .build();
        return data;
    }

    /** 관리자 회원정보 수정 */
    @PatchMapping("/{id}")
    public BaseResponse<String> edit(@PathVariable Long id,
                                     @ModelAttribute MemberInfoRequest request) {
        Member editMember = memberService.edit(id, request);
        return new BaseResponse<>(SUCCESS);
    }
}

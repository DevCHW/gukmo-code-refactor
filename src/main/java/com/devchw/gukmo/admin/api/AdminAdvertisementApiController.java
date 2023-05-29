package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.service.AdminAdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/advertisements")
public class AdminAdvertisementApiController {

    private final AdminAdvertisementService adminAdvertisementService;

    /** 관리자 회원내역 조회 */
    @PostMapping
    public DataTableResponse advertisements(@RequestBody MultiValueMap<String, String> formData) {
        int draw = Integer.parseInt(formData.get("draw").get(0));
        int start = Integer.parseInt(formData.get("start").get(0));
        int length = Integer.parseInt(formData.get("length").get(0));

        List<AdvertisementListDto> findData = adminAdvertisementService.findAllAdvertisementList(start, length, formData);
        int total = (int) adminAdvertisementService.findAllAdvertisementTotal(formData);

        DataTableResponse data = DataTableResponse.builder()
                .draw(draw)
                .recordsFiltered(total)
                .recordsTotal(total)
                .data(findData)
                .build();
        return data;
    }
}

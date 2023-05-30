package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.advertisement.AdvertisementDto;
import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementFormRequest;
import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.advertisement.UpdateAdvertisementRequest;
import com.devchw.gukmo.admin.service.AdvertisementService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/advertisements")
public class AdvertisementApiController {

    private final AdvertisementService adminAdvertisementService;

    /** 관리자 광고내역 조회 */
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

    /** 광고 삭제 */
    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteAdvertisement(@PathVariable("id") Long id) {
        adminAdvertisementService.delete(id);
        return new BaseResponse<>(SUCCESS);
    }

    /** 광고 수정 */
    @PatchMapping("/{id}")
    public BaseResponse<String> editAdvertisement(@PathVariable("id") Long id,
                                                  @ModelAttribute UpdateAdvertisementRequest request,
                                                  @RequestParam(required = false) MultipartFile advertisementFile) {
        Long changedId = adminAdvertisementService.edit(id, request, advertisementFile);
        return new BaseResponse<>(SUCCESS);
    }

    /** 광고 리스트 조회 */
    @GetMapping
    public BaseResponse<List<AdvertisementDto>> advertisementList() {
        List<AdvertisementDto> data = adminAdvertisementService.findAll();
        return new BaseResponse<>(data);
    }
}

package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.dto.advertisement.AdvertisementDto;
import com.devchw.gukmo.admin.service.AdminAdvertisementService;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/advertisements")
public class AdminAdvertisementController {
    private final AdminAdvertisementService adminAdvertisementService;

    /** 관리자 광고내역 조회 */
    @GetMapping
    public String advertisementList(Model model) {
        return "advertisement/advertisementList.tiles2";
    }

    /** 관리자 광고내역 상세조회 */
    @GetMapping("/{id}")
    public String advertisementDetail(@PathVariable Long id, Model model) {
        Advertisement findAdvertisement = adminAdvertisementService.findById(id);
        AdvertisementDto advertisement = new AdvertisementDto().toDto(findAdvertisement);
        model.addAttribute("advertisement", advertisement);
        return "advertisement/advertisementDetail.tiles2";
    }

    /** 광고배너 다운로드 */
//    @GetMapping("/{id}/download")
//    public String advertisementDownload(@PathVariable Long id, Model model) {
//        return "";
//    }
}

package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.dto.advertisement.AdvertisementDto;
import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementFormRequest;
import com.devchw.gukmo.admin.service.AdvertisementService;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.user.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/advertisements")
public class AdvertisementController {
    private final AdvertisementService adminAdvertisementService;

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

    /** 광고 등록 폼 */
    @GetMapping("/new")
    public String advertisementForm() {
        return "advertisement/advertisementForm.tiles2";
    }

    /** 광고 등록 폼 */
    @GetMapping("/calendar")
    public String calendar() {
        return "advertisement/calendar.tiles2";
    }

    /** 광고배너 다운로드 */
//    @GetMapping("/{id}/download")
//    public String advertisementDownload(@PathVariable Long id, Model model) {
//        return "";
//    }

    /** 광고 등록 */
    @PostMapping
    public String save(@ModelAttribute AdvertisementFormRequest form,
                       @RequestParam MultipartFile advertisementFile,
                       Model model) {
        System.out.println("form = " + form);
        System.out.println("advertisementFile = " + advertisementFile);
        Advertisement savedAdvertisement = adminAdvertisementService.save(form, advertisementFile);

        if(savedAdvertisement != null) {
            MessageResponse messageResponse = MessageResponse.builder()
                    .message("광고 등록에 성공하였습니다!")
                    .loc("/admin/advertisements/new")
                    .build();
            model.addAttribute("messageResponse", messageResponse);
        } else {
            MessageResponse messageResponse = MessageResponse.builder()
                    .message("서버 오류입니다.")
                    .loc("/admin/advertisements/new")
                    .build();
            model.addAttribute("messageResponse", messageResponse);
        }
        return "msg.tiles1";
    }
}

package com.devchw.gukmo.admin.controller;

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

    @GetMapping
    public String advertisementList(Model model) {
        return "advertisement/advertisementList.tiles2";
    }

//    @GetMapping("/{id}")
//    public String advertisementDetail(@PathVariable Long id) {
//        Advertisement findAdvertisement = adminAdvertisementService.findById(id);
//    }
}

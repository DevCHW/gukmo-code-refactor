package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @GetMapping
    public String memberList(Model model) {
        return "member/memberList.tiles2";
    }
}

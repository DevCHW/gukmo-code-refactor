package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 이용약관페이지 매핑
     */
    @GetMapping(value="/TOS")
    public String viewTOS() {
        return "member/TOS.tiles1";
    }
}

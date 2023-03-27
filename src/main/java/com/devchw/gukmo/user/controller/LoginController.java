package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.member.MemberLogin;
import com.devchw.gukmo.user.dto.LoginFormDto;
import com.devchw.gukmo.user.dto.LoginMemberDto;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인페이지 매핑
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute MemberLogin memberLogin) {
        return "login/login.tiles1";
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(String userid, String passwd,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        Member loginMember = loginService.login(userid, passwd);

        if(loginMember == null) {
            return "login/login.tiles1";
        }

        //로그인 성공처리
        HttpSession session = request.getSession();

        //세션에 회원정보 보관
        LoginMemberDto loginMemberDto = new LoginMemberDto(loginMember.getId(), loginMember.getAuthority());
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

        return "redirect:" + redirectURL;
    }
}

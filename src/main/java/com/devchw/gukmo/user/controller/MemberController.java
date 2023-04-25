package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.dto.member.MyPageDto;
import com.devchw.gukmo.user.dto.member.SignUpFormDto;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 이용약관 페이지
     */
    @GetMapping("/tos")
    public String tosPage() {
        return "member/tos.tiles1";
    }

    /**
     * 교육기관회원 가입 페이지
     */
    @GetMapping("/member/signUp/academy")
    public String academyMemberSignUpForm() {
        return "member/academy/signUpForm.tiles1";
    }

    /**
     * 일반회원 가입 페이지
     */
    @GetMapping("/member/signUp")
    public String signUpForm(@ModelAttribute SignUpFormDto form) {
        return "member/signUpForm.tiles1";
    }

    /**
     * 회원가입
     */
    @PostMapping("/member/signUp")
    public String save(@Valid @ModelAttribute SignUpFormDto form, BindingResult bindingResult) {
        log.info("회원가입 회원정보={}", form);
        if (bindingResult.hasErrors()) {    // SignUpForm 검증
            return "login/loginForm.tiles1";
        }

        Member member = form.toEntity(); //Dto -> 엔티티 변환

        memberRepository.save(member);
        return "redirect:/login";
    }

    /**
     * 마이페이지 - 내 계정 페이지
     */
    @GetMapping("/member/{id}/my/account")
    public String account(@PathVariable Long id, Model model) {
        Member loginMember = memberRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));
        model.addAttribute("myPage",new MyPageDto().toDto(loginMember));
        return "member/my/account.tiles1";
    }

    /**
     * 마이페이지 - 활동 내역 페이지
     */
    @GetMapping("/member/{id}/my/activities")
    public String activities(@PathVariable Long id, Model model) {
        Member loginMember = memberRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));
        model.addAttribute("myPage",new MyPageDto().toDto(loginMember));
        return "member/my/activities.tiles1";
    }

    /**
     * 마이페이지 - 내정보 페이지
     */
    @GetMapping("/member/{id}/my/info")
    public String info(@PathVariable Long id, Model model) {
        Member loginMember = memberRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));
        log.info("내정보 페이지 호출 member={}", loginMember);
        model.addAttribute("myPage",new MyPageDto().toDto(loginMember));
        return "member/my/info.tiles1";
    }
}

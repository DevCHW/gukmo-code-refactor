package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.board.get.CommunityListDto;
import com.devchw.gukmo.user.dto.member.MyPageDto;
import com.devchw.gukmo.user.dto.member.SignUpFormDto;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.ActivityService;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ActivityService activityService;

    /** 이용약관 페이지  */
    @GetMapping("/tos")
    public String tosPage() {
        return "member/tos.tiles1";
    }

    /** 교육기관회원 가입 페이지 */
    @GetMapping("/signUp/academy")
    public String academyMemberSignUpForm() {
        return "member/academy/signUpForm.tiles1";
    }

    /** 일반회원 가입 페이지 */
    @GetMapping("/signUp")
    public String signUpForm(@ModelAttribute SignUpFormDto form) {
        return "member/signUpForm.tiles1";
    }

    /** 회원가입 */
    @PostMapping("/signUp")
    public String save(@Valid @ModelAttribute SignUpFormDto form,
                       BindingResult bindingResult) {
        log.info("회원가입 회원정보={}", form);
        if (bindingResult.hasErrors()) {    // SignUpForm 검증
            return "login/loginForm.tiles1";
        }
        memberService.signUp(form); //회원가입시키기
        return "redirect:/login";
    }

    /** 마이페이지 - 내정보 페이지 */
    @GetMapping("/{id}/my/info")
    public String info(@PathVariable Long id, Model model) {
        Member loginMemberInfo = memberRepository.findMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        MyPageDto memberInfo = new MyPageDto().toDto(loginMemberInfo);
        log.info("마이페이지 - 내 정보 페이지 member={}", memberInfo);
        model.addAttribute("memberInfo", memberInfo);
        return "member/my/info.tiles1";
    }

    /** 마이페이지 - 내 계정 페이지 */
    @GetMapping("/{id}/my/account")
    public String account(@PathVariable Long id, Model model) {
        Member loginMemberInfo = memberRepository.findMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        MyPageDto memberInfo = new MyPageDto().toDto(loginMemberInfo);
        log.info("마이페이지 - 내 계정 페이지 member={}", memberInfo);
        model.addAttribute("memberInfo", memberInfo);
        return "member/my/account.tiles1";
    }

    /** 마이페이지 - 활동 내역 페이지 */
    @GetMapping("/{id}/my/activities")
    public String activities(@PathVariable Long id, Model model, Pageable pageable) {
        Member loginMemberInfo = memberRepository.findMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        MyPageDto memberInfo = new MyPageDto().toDto(loginMemberInfo);
        
        //페이징 활동내역 조회
//        Page<Activity> activities = activityService.findAllByMemberId(id, pageable);

        log.info("마이페이지 - 활동내역 페이지 member={}", memberInfo);
        model.addAttribute("memberInfo", memberInfo);

//        log.info("조회된 회원의 활동내역={}", activities);
        return "member/my/activities.tiles1";
    }
}

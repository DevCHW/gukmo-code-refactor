package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.member.AcademyMemberSignUpFormDto;
import com.devchw.gukmo.user.dto.member.ActivityDto;
import com.devchw.gukmo.user.dto.member.ActivityDto.WriterNicknameDto;
import com.devchw.gukmo.user.dto.member.MyPageDto;
import com.devchw.gukmo.user.dto.member.SignUpFormDto;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.ActivityService;
import com.devchw.gukmo.user.service.BoardService;
import com.devchw.gukmo.user.service.MemberService;
import com.devchw.gukmo.utils.PageBarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ActivityService activityService;
    private final BoardService boardService;

    /** 이용약관 페이지  */
    @GetMapping("/tos")
    public String tosPage() {
        return "member/tos.tiles1";
    }

    /** 교육기관회원 가입 페이지 */
    @GetMapping("/signUp/academyMember")
    public String academyMemberSignUpForm() {
        return "member/academyMemberSignUpForm.tiles1";
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
        log.info("SignUpFormDto={}", form);
        if (bindingResult.hasErrors()) {    // SignUpForm 검증
            return "member/signUpForm.tiles1";
        }
        memberService.signUp(form); //회원가입시키기
        return "redirect:/login";
    }

    /** 교육기관회원 회원가입 */
    @PostMapping("/signUp/academyMember")
    public String saveAcademyMember(@Valid @ModelAttribute AcademyMemberSignUpFormDto form,
                       BindingResult bindingResult) {
        log.info("AcademyMemberSignUpFormDto={}", form);
        if (bindingResult.hasErrors()) {    // SignUpForm 검증
            return "member/academyMemberSignUpForm.tiles1";
        }
        memberService.signUpAcademyMember(form); //회원가입시키기
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
        //페이징 활동내역 조회
        Page<Activity> activityList = activityService.findAllByMemberId(id, pageable);
        List<Long> boardIds = activityList.getContent().stream().map(a -> a.getBoard().getId()).collect(Collectors.toList());
        List<WriterNicknameDto> writerNicknames = boardService.findAllWriterNicknamesByBoardId(boardIds);

        List<ActivityDto> activities = activityList.stream().map(a -> new ActivityDto().toDto(a)).collect(Collectors.toList());


        // 페이지 바 만들기
        String queryString = "";
        String url = "/"+id+"/my/activities";
        int totalPage = activityList.getTotalPages();
        String pageBar = PageBarUtil.createPageBar(pageable.getPageNumber(), totalPage, url, queryString);
        log.info("조회된 activities={}", activities);
        log.info("조회된 writerNicknames={}", writerNicknames);

        model.addAttribute("activities",activities);
        model.addAttribute("writerNicknames",writerNicknames);
        model.addAttribute("pageBar",pageBar);

        return "member/my/activities.tiles1";
    }

}

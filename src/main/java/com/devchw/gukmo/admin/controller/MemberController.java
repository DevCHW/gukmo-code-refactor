package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.dto.member.MemberDto;
import com.devchw.gukmo.admin.repository.AdminOauthRepository;
import com.devchw.gukmo.admin.service.MemberService;
import com.devchw.gukmo.entity.login.Oauth;
import com.devchw.gukmo.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class MemberController {
    private final MemberService adminMemberService;
    private final AdminOauthRepository adminOauthRepository;

    /** 회원 리스트 페이지 */
    @GetMapping
    public String memberList(Model model) {
        return "member/memberList.tiles2";
    }

    /** 회원 닉네임으로 상세조회 */
    @GetMapping("/{nickname}")
    public String memberDetail(@PathVariable String nickname, Model model) {
        Member findMember = adminMemberService.findByNickname(nickname);
        MemberDto member = new MemberDto().toDto(findMember);
        List<Oauth> findOauthList = adminOauthRepository.findAllByMemberId(member.getId());
        List<String> oauthList = findOauthList.stream().map(o -> o.getType().name()).collect(Collectors.toList());

        model.addAttribute("member", member);
        model.addAttribute("oauthList", oauthList);
        return "member/memberDetail.tiles2";
    }
}

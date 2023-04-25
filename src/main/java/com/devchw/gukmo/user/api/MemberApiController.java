package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.api.member.UpdateMemberInfoRes;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 회원 아이디로 이메일 조회 API
     */
    @GetMapping("/api/member/{id}/email")
    public BaseResponse<String> getEmail(@PathVariable Long id) {
        String email = memberRepository.findEmailById(id);
        return new BaseResponse<>(email);
    }

    /**
     * 회원 정보 수정
     */
    @PatchMapping("/api/member/{id}")
    public BaseResponse updateInfo(@PathVariable Long id,
                                    @RequestParam Map<String, Object> req,
                                    @RequestParam MultipartFile profileImage,
                                    MultipartHttpServletRequest mrequest) {
        log.info("회원 정보 수정 호출 REQUEST={}", req);
        UpdateMemberInfoRes updateMemberInfoRes = new UpdateMemberInfoRes();
        return new BaseResponse(true);
    }
}

package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.api.member.MemberInfoResponse;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /** 이메일 존재여부 조회 */
    @GetMapping("/email/exist")
    public BaseResponse<Boolean> emailExistCheck(@RequestParam String email) {
        return new BaseResponse<>(memberRepository.existsByEmail(email));
    }

    /** 닉네임 존재여부 조회 */
    @GetMapping("/nickname/exist")
    public BaseResponse<Boolean> nicknameExistCheck(@RequestParam String nickname) {
        return new BaseResponse<>(memberRepository.existsByNickname(nickname));
    }

    /** 회원 정보 수정 (미완성)*/
    @PatchMapping("/{id}")
    public BaseResponse<Boolean> updateInfo(@PathVariable Long id,
                                    @RequestParam Map<String, Object> req,
                                    @RequestParam MultipartFile profileImage,
                                    MultipartHttpServletRequest mrequest) {
        log.info("회원 정보 수정 요청 REQUEST={}", req);
        MemberInfoResponse updateMemberInfoRes = new MemberInfoResponse();
        return new BaseResponse<>(MEMBER_INFO_CHANGE_SUCCESS);
    }

    /** 회원 삭제 */
    @DeleteMapping("/{id}")
    public BaseResponse<String> delete(@PathVariable("id") Long id) {
        log.info("계정삭제 요청 memberId={}", id);
        memberRepository.deleteById(id);
        return new BaseResponse<>(MEMBER_DELETE_SUCCESS);
    }

    /** 회원 이메일 변경 */
    @PatchMapping("/{id}/email")
    public BaseResponse<String> changeEmail(@PathVariable("id") Long id,
                                             @RequestParam String email) {
        log.info("이메일 변경 요청 memberId={}", id);
        memberService.editEmail(id, email);
        return new BaseResponse<>(MEMBER_INFO_CHANGE_SUCCESS);
    }
}

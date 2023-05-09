package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.dto.member.UpdateInfoRequest;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import com.devchw.gukmo.utils.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FileManager fileManager;

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

    /** 회원 정보 수정 */
    @PatchMapping("/{id}")
    public BaseResponse<String> updateInfo(@PathVariable Long id,
                                            @ModelAttribute UpdateInfoRequest request) throws IOException {
        memberService.changeInfo(id, request);
        return new BaseResponse<>(MEMBER_INFO_CHANGE_SUCCESS);
    }

    /** 회원 삭제 */
    @DeleteMapping("/{id}")
    public BaseResponse<String> delete(@PathVariable("id") Long id,
                                       HttpSession session) {
        log.info("계정삭제 요청 memberId={}", id);

        memberRepository.deleteById(id);

        LoginMemberDto loginMemberInfo = (LoginMemberDto) session.getAttribute("loginMember");

        //사용자가 설정한 프로필이미지 파일 삭제하기.
        if(!loginMemberInfo.getProfileImage().equals("user.PNG")) { //기본이미지가 아니라면.
            fileManager.delete(loginMemberInfo.getProfileImage());
        }
        session.invalidate();
        return new BaseResponse<>(MEMBER_DELETE_SUCCESS);
    }

    /** 회원 정보수정(이메일) */
    @PatchMapping("/{id}/email")
    public BaseResponse<String> changeEmail(@PathVariable("id") Long id,
                                             @RequestParam String email) {
        log.info("이메일 변경 요청 memberId={}", id);
        memberService.editEmail(id, email);
        return new BaseResponse<>(MEMBER_INFO_CHANGE_SUCCESS);
    }
}

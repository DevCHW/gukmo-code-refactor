package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.repository.AcademyMemberRepository;
import com.devchw.gukmo.user.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signUp")
public class SignUpApiController {

    private final LoginRepository loginRepository;
    private final AcademyMemberRepository academyMemberRepository;

    /** 회원아이디 존재여부 조회 */
    @GetMapping("/userId/exist")
    public BaseResponse<Boolean> userIdExistCheck(@RequestParam String userId) {
        return new BaseResponse<>(loginRepository.existsByUserId(userId));
    }

    /** 교육기관명 존재여부 조회 */
    @GetMapping("/academyName/exist")
    public BaseResponse<Boolean> academyNameExistCheck(@RequestParam String academyName) {
        return new BaseResponse<>(academyMemberRepository.existsByAcademyName(academyName));
    }
}

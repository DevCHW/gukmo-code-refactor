package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.devchw.gukmo.config.response.BaseResponseStatus.PASSWORD_CHANGE_SUCCESS;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginApiController {

    private final LoginRepository loginRepository;
    private final LoginService loginService;

    /** 비밀번호 값 확인 */
    @GetMapping("/{id}/password")
    public BaseResponse<Boolean> EqualsOriginPasswordCheck (@PathVariable("id") Long id,
                                                            @RequestParam String password) {
        return new BaseResponse<>(loginService.EqualsOriginPasswordCheck(id, password));
    }

    /** 비밀번호 변경하기 */
    @PatchMapping("/{id}/password")
    public BaseResponse<String> editPassword (@PathVariable("id") Long id,
                                              @RequestParam String password) {
        log.info("비밀번호 변경요청 id={}", id);
        loginService.editPassword(id, password);
        return new BaseResponse<>(PASSWORD_CHANGE_SUCCESS);
    }
}

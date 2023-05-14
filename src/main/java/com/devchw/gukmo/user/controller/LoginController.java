package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.exception.LoginException;
import com.devchw.gukmo.user.dto.MessageResponse;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.dto.login.LoginFormDto;
import com.devchw.gukmo.user.service.LoginService;
import com.devchw.gukmo.user.service.NaverLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final NaverLoginService naverLoginService;

    /**
     * 로그인페이지
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginFormDto form,
                            @RequestParam(defaultValue = "/") String redirectURL,
                            HttpSession session,
                            Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return "login/loginForm.tiles1";
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormDto form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        Model model,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {    // LoginFormDto 검증
            return "login/loginForm.tiles1";
        }

        //로그인 처리하기
        try {
            Member loginMember = loginService.login(form.getUserId(), form.getPassword());

            //Member -> LoginMemberDto 변환
            LoginMemberDto loginMemberDto = LoginMemberDto.toDto(loginMember);

            //session에 필요한 값만 저장.
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

            log.info("로그인 성공, member{}", loginMemberDto);
            log.info("redirectURL={}", redirectURL);
            if(loginMemberDto.getUserRole().equals(Member.UserRole.ADMIN)) {    //관리자일 경우 관리자 페이지
                return "redirect:/admin";
            } else {
                return "redirect:" + URLDecoder.decode(redirectURL, "UTF-8");
            }
        } catch (LoginException e) { //로그인 실패(LoginException 예외 처리)

            log.info("로그인 실패");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            MessageResponse messageResponse = MessageResponse.builder()
                    .message("아이디 또는 비밀번호가 맞지 않습니다.")
                    .loc("/login")
                    .build();
            model.addAttribute("messageResponse", messageResponse);
            return "msg.tiles1";

        } catch (UnsupportedEncodingException e) {
            throw new BaseException(ENCODING_ERROR);
        }
    }

    /**
     * 로그아웃 처리
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request,
                         @RequestParam(defaultValue = "/") String redirectURL) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("로그아웃 memberId={}", session.getAttribute(SessionConst.LOGIN_MEMBER));
            session.invalidate();
        }
        try {
            return "redirect:" + URLDecoder.decode(redirectURL, "UTF-8");
        } catch (UnsupportedEncodingException e) {  //인코딩 에러
            throw new BaseException(ENCODING_ERROR);
        }
    }
}

package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.user.dto.api.oauth.OauthLoginRequest;
import com.devchw.gukmo.user.dto.api.oauth.OauthLoginResponse;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.service.NaverLoginService;
import com.devchw.gukmo.user.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuthApiController {

    private final OAuthService oauthService;
    private final NaverLoginService naverLoginService;

    /** 카카오로그인 */
    @PostMapping("/kakao")
    public BaseResponse<OauthLoginResponse> kakaoLogin(OauthLoginRequest kakaoLoginRequest, HttpSession session) {
        Member member = oauthService.oauthLogin(kakaoLoginRequest);

        LoginMemberDto loginMemberDto = LoginMemberDto.toDto(member);

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

        OauthLoginResponse kakaoLoginResponse = OauthLoginResponse.builder()
                .redirectURL(kakaoLoginRequest.getRedirectURL())
                .build();
        return new BaseResponse<>(kakaoLoginResponse);
    }

    /** 페이스북로그인 */
    @PostMapping("/facebook")
    public BaseResponse<OauthLoginResponse> facebookLogin(OauthLoginRequest facebookLoginRequest, HttpSession session) {
        Member member = oauthService.oauthLogin(facebookLoginRequest);

        LoginMemberDto loginMemberDto = LoginMemberDto.toDto(member);

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

        OauthLoginResponse facebookLoginResponse = OauthLoginResponse.builder()
                .redirectURL(facebookLoginRequest.getRedirectURL())
                .build();
        return new BaseResponse<>(facebookLoginResponse);
    }

    /** 구글로그인 */
    @PostMapping("/google")
    public BaseResponse<OauthLoginResponse> googleLogin(OauthLoginRequest googleLoginRequest, HttpSession session) {
        Member member = oauthService.oauthLogin(googleLoginRequest);

        LoginMemberDto loginMemberDto = LoginMemberDto.toDto(member);

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

        OauthLoginResponse googleLoginResponse = OauthLoginResponse.builder()
                .redirectURL(googleLoginRequest.getRedirectURL())
                .build();
        return new BaseResponse<>(googleLoginResponse);
    }


    /** 네이버로그인 폼 url 얻기 */
    @GetMapping("/naver/url")
    public BaseResponse<String> naverLogin(@RequestParam(defaultValue = "/") String redirectURL,
                                           HttpSession session) {
        String naverLoginURL = naverLoginService.getAuthorizationUrl(session);
        session.setAttribute("redirectURL", redirectURL);
        return new BaseResponse<>(naverLoginURL);
    }
}

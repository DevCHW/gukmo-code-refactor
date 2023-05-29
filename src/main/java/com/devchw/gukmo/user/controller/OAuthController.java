package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.oauth.OauthLoginRequest;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.service.NaverLoginService;
import com.devchw.gukmo.user.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.devchw.gukmo.config.response.BaseResponseStatus.JSON_PROCESSING_ERROR;
import static com.devchw.gukmo.config.response.BaseResponseStatus.NAVER_LOGIN_SUCCESS;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final NaverLoginService naverLoginService;
    private final OAuthService oauthService;

    /** 네이버로그인 */
    @GetMapping("/naver")
    public String naverLogin(@RequestParam String code,
                             @RequestParam String state,
                             HttpSession session) {
        //엑세스 토큰 발급
        OAuth2AccessToken accessToken = naverLoginService.getAccessToken(session, code, state);

        //엑세스토큰으로 사용자 정보 요청
        String response = naverLoginService.getUserProfile(accessToken);

        try {
            ObjectMapper objectMapper =new ObjectMapper();
            Map<String, Object> naverUserInfo = (Map<String, Object>) objectMapper.readValue(response, Map.class).get("response");
            OauthLoginRequest naverLoginRequest = OauthLoginRequest.builder()
                    .authId((String)naverUserInfo.get("id"))
                    .email((String)naverUserInfo.get("email"))
                    .type("NAVER")
                    .profileImage((String)naverUserInfo.get("profile_image"))
                    .username((String)naverUserInfo.get("name"))
                    .build();

            Member member = oauthService.oauthLogin(naverLoginRequest);
            LoginMemberDto loginMemberDto = new LoginMemberDto().toDto(member);

            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

            String redirectURL = (String) session.getAttribute("redirectURL");
            log.info("redirectURL="+redirectURL);
            session.removeAttribute("redirectURL");
            return "redirect:" + redirectURL;
        } catch (JsonProcessingException e) {
            throw new BaseException(JSON_PROCESSING_ERROR);
        }
    }
}

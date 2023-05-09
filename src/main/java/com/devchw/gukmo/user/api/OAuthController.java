package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.oauth.OauthLoginRequest;
import com.devchw.gukmo.user.dto.api.oauth.OauthLoginResponse;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.service.NaverLoginService;
import com.devchw.gukmo.user.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuthController {

    private final OAuthService oauthService;
    private final NaverLoginService naverLoginService;

    /** 카카오로그인 */
    @PostMapping("/kakao")
    public BaseResponse<OauthLoginResponse> kakaoLogin(OauthLoginRequest kakaoLoginRequest, HttpSession session) {
        Member member = oauthService.kakaoLogin(kakaoLoginRequest);

        LoginMemberDto loginMemberDto = LoginMemberDto.toDto(member);

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberDto);

        OauthLoginResponse kakaoLoginResponse = OauthLoginResponse.builder()
                .redirectURL(kakaoLoginRequest.getRedirectURL())
                .build();
        return new BaseResponse<>(kakaoLoginResponse);
    }

    /** 네이버로그인 처리 */
    @GetMapping("/naver")
    public String authNaver(@RequestParam String code,
                            @RequestParam String state,
                            HttpSession session) {
        OAuth2AccessToken oauthToken = naverLoginService.getAccessToken(session, code, state);

        String apiResult = naverLoginService.getUserProfile(oauthToken);

//        날아온 토큰값 확인
		System.out.println("확인용 apiResult =>"+apiResult);

        ObjectMapper objectMapper =new ObjectMapper();

        Map<String, Object> apiJson = null;
        try {
            objectMapper.readValue(apiResult, Map.class).get("response");
        } catch (JsonProcessingException e) {
            throw new BaseException(JSON_PROCESSING_ERROR);
        }

//		System.out.println("확인용 apiJson =>"+apiJson);

//        Map<String, Object> naverConnectionCheck = service.kakaoConnectionCheck(apiJson);
//
//        if(naverConnectionCheck == null) { //신규 회원이라면
//
//            request.setAttribute("username",apiJson.get("name"));
//            request.setAttribute("email",apiJson.get("email"));
//            request.setAttribute("userid",apiJson.get("id"));
//            request.setAttribute("profile_image",apiJson.get("profile_image"));
//
//            return "login/naverMemberJoinForm.tiles1";
//
//        }else if("0".equals(naverConnectionCheck.get("NAVER")) && naverConnectionCheck.get("EMAIL") != null) { //기존회원이 네이버로그인을 시도하였다면
//            service.setNaverConnection(apiJson);	//네이버연동회원으로 업데이트 해주기
//            String userid = service.getUserid((String)apiJson.get("email"));
//            MemberVO user = service.getUser(userid);
//            session.setAttribute("user", user);
//
//        }else { //기존 네이버회원연동회원이라면
//            String userid = service.getUserid((String)apiJson.get("email"));
//            MemberVO user = service.getUser(userid);
//            session.setAttribute("user", user);
//        }
//        return "index.tiles1";
        return "code : " + code;
    }
}

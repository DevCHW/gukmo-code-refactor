package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.login.Oauth;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.oauth.OauthLoginRequest;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.repository.OAuthRepository;
import com.devchw.gukmo.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {

    private final OAuthRepository oauthRepository;
    private final MemberRepository memberRepository;

    /** 카카오로그인 */
    @Transactional
    public Member kakaoLogin(OauthLoginRequest kakaoLoginRequest) {
        Oauth.Type type = kakaoLoginRequest.oauthTypeConverter();
        Optional<Oauth> findOAuth = oauthRepository.findByIdAndType(kakaoLoginRequest.getOauthId(), type);
        Optional<Member> findMember = memberRepository.findByEmail(kakaoLoginRequest.getEmail());

        if(findOAuth.isEmpty()) {   //기존 Oauth 회원이 아니라면
            if(findMember.isEmpty()) {  //기존 회원도 아니라면
                Member saveMember = kakaoLoginRequest.toMemberEntity(randomNicknameGenerated());

                Member savedMember = memberRepository.save(saveMember);
                Oauth oauth = kakaoLoginRequest.toOauthEntity(savedMember);
                oauthRepository.save(oauth);
                return savedMember;
            } else {    //기존 회원이지만 Oauth 회원이 아니라면 연동.
                Member saveMember = findMember.orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
                Oauth oauth = Oauth.builder()
                        .id(kakaoLoginRequest.getOauthId())
                        .type(type)
                        .member(saveMember)
                        .build();
                oauthRepository.save(oauth);
                return saveMember;
            }
        } else {    //기존에 존재하는 회원이고, Oauth 회원이라면 회원정보 반환
            return findOAuth.orElseThrow(() -> new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR))
                    .getMember();
        }
    }

    /** 랜덤한 닉네임 생성하기 */
    private String randomNicknameGenerated() {
        String randomNumber = NumberUtil.createRandomNumber(3);
        int randomChoiceNumber = Integer.parseInt(NumberUtil.createRandomNumber(1));
        String[] defaultNickname = {"푸른물고기", "달콤귀여움", "노란바나나", "새벽달빛", "빨간벽돌", "파란구름", "밝은별빛", "검은향기", "하얀솜사탕", "보라빛선물"};
        return defaultNickname[randomChoiceNumber] + randomNumber;
    }
}

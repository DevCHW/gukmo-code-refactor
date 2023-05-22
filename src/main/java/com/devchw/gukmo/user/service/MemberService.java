package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.member.SignUpFormDto;
import com.devchw.gukmo.user.dto.api.member.UpdateInfoRequest;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.utils.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;
    private final FileManager fileManager;

    /** 회원가입 */
    @Transactional
    public void signUp(SignUpFormDto form) {
        Member member = form.toMemberEntity(); //Dto -> 엔티티 변환
        Member saveMember = memberRepository.save(member);
        Login login = form.toLoginEntity(saveMember);
        loginRepository.save(login);
    }

    /** 이메일 수정 */
    @Transactional
    public void editEmail(Long id, String email) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        member.changeMemberInfo(email);
    }

    /** 프로필이미지, 닉네임, 회원명, 이메일 수신동의여부 수정 */
    @Transactional
    public void changeInfo(Long id, UpdateInfoRequest request) {
        //기존 저장되어있는 프로필이미지가 기본이미지가 아니라면 기존 이미지파일 삭제.
        Member loginMember = memberRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        String originProfileImage = loginMember.getProfileImage();

        if(!originProfileImage.equals("user.PNG")) {
            fileManager.delete(originProfileImage);
        }

        // 새로 저장될 프로필이미지 저장하기
        String savedFileName = fileManager.save(request.getProfileImage());

        // DB 반영
        if(request.getProfileImage().isEmpty()) {   //프로필이미지가 없을 때
            if(request.getEmailAccept().equals("YES")) {
                loginMember.changeMemberInfo(request.getUsername(), request.getNickname(), Member.EmailAccept.YES);
            } else {
                loginMember.changeMemberInfo(request.getUsername(), request.getNickname(), Member.EmailAccept.NO);
            }
        } else {    //프로필이미지가 있을 때
            if(request.getEmailAccept().equals("YES")) {
                loginMember.changeMemberInfo(request.getUsername(), request.getNickname(), savedFileName, Member.EmailAccept.YES);
            } else {
                loginMember.changeMemberInfo(request.getUsername(), request.getNickname(), savedFileName, Member.EmailAccept.NO);
            }
        }
    }
}

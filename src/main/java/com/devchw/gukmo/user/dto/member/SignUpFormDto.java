package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignUpFormDto {
    @NotEmpty
    private String userid;

    @NotEmpty
    private String passwd;

    @NotEmpty
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String emailAccept;

    /**
     * Dto -> Entity 변환
     */
    public Login toLoginEntity(Member saveMember) {
        return Login.builder()
                .userId(userid)
                .member(saveMember)
                .password(passwd)
                .build();
    }

    public Member toMemberEntity() {
        if(emailAccept.equals("YES")) {
            return Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .emailAccept(Member.EmailAccept.YES)
                    .userRole(Member.UserRole.MEMBER)
                    .build();
        } else {
            return Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .emailAccept(Member.EmailAccept.NO)
                    .userRole(Member.UserRole.MEMBER)
                    .build();
        }
    }
}

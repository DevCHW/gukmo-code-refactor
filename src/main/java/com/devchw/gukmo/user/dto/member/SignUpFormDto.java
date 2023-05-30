package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.utils.SHA256;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

import static com.devchw.gukmo.entity.member.Member.*;

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
     * Dto -> Entity
     */

    public Member toEntity() {
        Login login = Login.builder()
                .userId(userid)
                .password(SHA256.encrypt(passwd))
                .build();
        EmailAccept emailAccept = this.emailAccept.equals("YES") ? EmailAccept.YES : EmailAccept.NO;
        return builder()
                .nickname(nickname)
                .username(username)
                .email(email)
                .login(login)
                .emailAccept(emailAccept)
                .userRole(UserRole.MEMBER)
                .build();
    }
}

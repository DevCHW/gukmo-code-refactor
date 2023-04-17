package com.devchw.gukmo;

import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginTestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        /**
         * 테스트용 아이디 추가
         */
        public void dbInit1() {
            log.info("테스트 회원 데이터를 추가합니다.");
            Member member = Member.builder()
                    .nickname("테스트회원")
                    .username("테스트")
                    .email("test@naver.com")
                    .point(0L)
                    .emailAccept(Member.EmailAccept.NO)
                    .userRole(Member.UserRole.MEMBER)
                    .build();

            Login loginMember = Login.builder()
                    .userId("test123")
                    .password("qwer1234")
                    .member(member)
                    .build();
            em.persist(member);
            em.persist(loginMember);
        }
    }
}

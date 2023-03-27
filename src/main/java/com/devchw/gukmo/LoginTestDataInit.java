package com.devchw.gukmo;

import com.devchw.gukmo.entity.member.Authority;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.member.MemberLogin;
import com.devchw.gukmo.entity.member.Status;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
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
            Member member =  new Member("테스트 회원", "최현우","user.PNG", "ggoma003@naver.com", 0, LocalDateTime.now(), Status.YES, Status.NO, Status.NO, Status.NO, Status.NO, Authority.USER);
            MemberLogin memberLogin = new MemberLogin(member, "ggoma003", "qwer1234$", LocalDateTime.now());
            em.persist(member);
            em.persist(memberLogin);
        }
    }
}

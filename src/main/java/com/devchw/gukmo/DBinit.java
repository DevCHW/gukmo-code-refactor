package com.devchw.gukmo;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.entity.board.Hashtag;
import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBinit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1(); //테스트용 아이디 추가
        initService.dbInit2(); //테스트 해시태그가 있는 게시물 넣기
        initService.dbInit3(); //테스트 게시글 넣기
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberRepository memberRepository;

        /**
         * 1. 테스트용 아이디 추가
         */
        public void dbInit1() {
            log.info("테스트 회원 데이터를 추가합니다.");
            Member member1 = Member.builder()
                    .nickname("테스트회원1")
                    .username("테스트1")
                    .email("test@naver.com")
                    .point(0L)
                    .emailAccept(Member.EmailAccept.NO)
                    .userRole(Member.UserRole.MEMBER)
                    .build();

            Member member2 = Member.builder()
                    .nickname("테스트회원2")
                    .username("테스트2")
                    .email("test@naver.com")
                    .point(0L)
                    .emailAccept(Member.EmailAccept.YES)
                    .userRole(Member.UserRole.MEMBER)
                    .build();

            Login loginMember1 = Login.builder()
                    .userId("test123")
                    .password("qwer1234")
                    .member(member1)
                    .build();

            Login loginMember2 = Login.builder()
                    .userId("ggoma003")
                    .password("qwer1234")
                    .member(member2)
                    .build();
            em.persist(member1);
            em.persist(member2);
            em.persist(loginMember1);
            em.persist(loginMember2);
        }

        /**
         * 3. 테스트 게시글 넣기
         */
        public void dbInit2() {
            for(int i=1; i<=1000; i++) {
                Member member = memberRepository.findById(1l).get();

                Board board = Board.builder()
                        .subject("테스트 글제목"+i)
                        .content("테스트 글내용"+i)
                        .firstCategory("커뮤니티")
                        .secondCategory("자유")
                        .member(member)
                        .build();

                em.persist(board);
            }//end of for--
        }

        /**
         * 2. 테스트 해시태그가 있는 게시물 넣기
         */
        public void dbInit3() {
            Member member = memberRepository.findById(2l).get();

            Board board = Board.builder()
                    .subject("해시태그가 있는 글제목")
                    .content("해시태그가 있는 글내용")
                    .firstCategory("커뮤니티")
                    .secondCategory("자유")
                    .member(member)
                    .build();

            Hashtag hashtag1 = Hashtag.builder()
                    .tagName("테스트해시태그1")
                    .build();

            Hashtag hashtag2 = Hashtag.builder()
                    .tagName("테스트해시태그2")
                    .build();

            BoardHashtag boardHashtag1 = BoardHashtag.builder()
                    .board(board)
                    .hashtag(hashtag1)
                    .build();

            BoardHashtag boardHashtag2 = BoardHashtag.builder()
                    .board(board)
                    .hashtag(hashtag2)
                    .build();
            em.persist(board);
            em.persist(hashtag1);
            em.persist(hashtag2);
            em.persist(boardHashtag1);
            em.persist(boardHashtag2);
        }
    }
}

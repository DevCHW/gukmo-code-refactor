package com.devchw.gukmo;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.board.Academy;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Curriculum;
import com.devchw.gukmo.entity.board.Notice;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.entity.hashtag.Hashtag;
import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.AcademyMember;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.utils.DateUtil;
import com.devchw.gukmo.utils.SHA256;
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
        initService.dbInit4(); //테스트 광고 넣기
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

            Login loginMember1 = Login.builder()
                    .userId("test123")
                    .password(SHA256.encrypt("qwer1234$"))
                    .build();

            Login loginMember2 = Login.builder()
                    .userId("ggoma003")
                    .password(SHA256.encrypt("qwer1234$"))
                    .build();

            Login loginMember3 = Login.builder()
                    .userId("admin")
                    .password(SHA256.encrypt("qwer1234$"))
                    .build();

            Member member1 = Member.builder()
                    .nickname("테스트회원1")
                    .username("테스트1")
                    .email("test@naver.com")
                    .emailAccept(Member.EmailAccept.NO)
                    .login(loginMember1)
                    .userRole(Member.UserRole.MEMBER)
                    .build();

            AcademyMember academyMember = AcademyMember.builder()
                    .academyName("쌍용강북교육센터")
                    .companyNum("214-85-29296")
                    .tel("02-336-8546")
                    .homepage("https://www.sist.co.kr/employment/gangbuk/index.jsp")
                    .build();

            Member member2 = Member.builder()
                    .nickname("테스트회원2")
                    .username("테스트2")
                    .email("test@naver.com")
                    .emailAccept(Member.EmailAccept.YES)
                    .academyMember(academyMember)
                    .login(loginMember2)
                    .userRole(Member.UserRole.ACADEMY)
                    .build();

            Member member3 = Member.builder()
                    .nickname("국모관리자")
                    .username("최현우")
                    .email("admin@naver.com")
                    .emailAccept(Member.EmailAccept.YES)
                    .login(loginMember3)
                    .userRole(Member.UserRole.ADMIN)
                    .build();

            em.persist(loginMember1);
            em.persist(loginMember2);
            em.persist(loginMember3);
            em.persist(member1);
            em.persist(academyMember);
            em.persist(member2);
            em.persist(member3);
        }

        /**
         * 2. 테스트 게시글 넣기
         */
        public void dbInit2() {
            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(4L).get();

                Board board = Board.builder()
                        .subject("자유게시판"+i)
                        .content("자유게시판"+i)
                        .firstCategory("커뮤니티")
                        .secondCategory("자유")
                        .member(member)
                        .build();

                em.persist(board);
            }//end of for--

            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(4L).get();

                Board board = Board.builder()
                        .subject("QnA"+i)
                        .content("QnA"+i)
                        .firstCategory("커뮤니티")
                        .secondCategory("QnA")
                        .member(member)
                        .build();

                em.persist(board);
            }//end of for--

            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(4L).get();

                Board board = Board.builder()
                        .subject("스터디"+i)
                        .content("스터디"+i)
                        .firstCategory("커뮤니티")
                        .secondCategory("스터디")
                        .member(member)
                        .build();

                em.persist(board);
            }//end of for--

            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(6L).get();

                Board board = Board.builder()
                        .subject("취미모임"+i)
                        .content("취미모임"+i)
                        .firstCategory("커뮤니티")
                        .secondCategory("취미모임")
                        .member(member)
                        .build();

                em.persist(board);
            }//end of for--

            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(6L).get();

                Board board = Board.builder()
                        .subject("수강/취업후기"+i)
                        .content("수강/취업후기"+i)
                        .firstCategory("커뮤니티")
                        .secondCategory("수강/취업후기")
                        .member(member)
                        .build();

                em.persist(board);
            }//end of for--

            //국비학원 데이터 넣기
            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(6L).get();
                Academy academy = Academy.builder()
                        .subject("스프링교육센터"+i)
                        .content("자바를 가르치는 학원입니다."+i)
                        .firstCategory("국비학원")
                        .secondCategory("국비학원")
                        .member(member)
                        .representativeName("최현우")
                        .address("서울시 강북구 한천로 404길 Java빌딩 500호")
                        .phone("01012345678")
                        .academyImage("쌍용강북교육센터.PNG")
                        .homepage("https://github.com/DevCHW/gukmo-code-refactor")
                        .build();
                em.persist(academy);
            }//end of for--

            // 교육과정 데이터 넣기
            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(6L).get();
                Curriculum curriculum = Curriculum.builder()
                        .subject("자바 백엔드과정"+i)
                        .content("자바 백엔드과정"+i)
                        .firstCategory("국비학원")
                        .secondCategory("교육과정")
                        .member(member)
                        .coreTechnology("Java, Spring, JPA")
                        .academyName("쌍용강북교육센터"+i)
                        .curriculumStartDate(DateUtil.stringToLocalDateTimeConverter("2023-06-03"))
                        .curriculumEndDate(DateUtil.stringToLocalDateTimeConverter("2023-12-30"))
                        .recruitmentStartDate(DateUtil.stringToLocalDateTimeConverter("2023-05-20"))
                        .recruitmentEndDate(DateUtil.stringToLocalDateTimeConverter("2023-12-30"))
                        .recruitsCount(0)
                        .url("https://www.sist.co.kr/employment/gangbuk/index.jsp")
                        .recruitmentPeriod(10)
                        .curriculumPeriod(300)
                        .build();
                em.persist(curriculum);
            }//end of for--

            // 공지사항 데이터 넣기
            for(int i=1; i<=100; i++) {
                Member member = memberRepository.findById(6L).get();
                Notice notice = Notice.builder()
                        .subject("공지사항"+i)
                        .content("공지사항 글내용" + i)
                        .firstCategory("공지사항")
                        .secondCategory("공지사항")
                        .member(member)
                        .build();
                em.persist(notice);
            }//end of for--

            // 공지사항 데이터 넣기
            for(int i=1; i<=3; i++) {
                Member member = memberRepository.findById(6L).get();
                Notice notice = Notice.builder()
                        .subject("[필독!] 안녕하세요! 최현우입니다."+i)
                        .content("안녕하세요! 해당 게시판 개발자 최현우입니다. <br> 모든 소스코드는 https://github.com/DevCHW/gukmo-code-refactor 에서 확인하실 수 있습니다. <br>감사합니다!")
                        .firstCategory("공지사항")
                        .secondCategory("공지사항")
                        .member(member)
                        .mustRead(Notice.MustRead.YES)
                        .build();
                em.persist(notice);
            }//end of for--
        }

        /**
         * 3. 테스트 해시태그가 있는 게시물 넣기
         */
        public void dbInit3() {
            Member member = memberRepository.findById(6L).get();

            Board board = Board.builder()
                    .subject("국비의 모든것에 오신것을 환영합니다!")
                    .content("재미있게 둘러봐주세요. 테스트계정은 아이디 test123, 비밀번호 qwer1234$ 입니다.")
                    .firstCategory("커뮤니티")
                    .secondCategory("자유")
                    .member(member)
                    .build();

            Hashtag hashtag1 = Hashtag.builder()
                    .tagName("Java")
                    .build();

            Hashtag hashtag2 = Hashtag.builder()
                    .tagName("Spring")
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

        /**
         * 메인페이지 광고 넣기
         */
        public void dbInit4() {
            for(int i=1; i<=3; i++) {
                Advertisement advertisement1 = Advertisement.builder()
                        .type(Advertisement.Type.MAIN)
                        .url("https://github.com/DevCHW/gukmo-code-refactor")
                        .startDate(DateUtil.stringToLocalDateTimeConverter("2023-01-01"))
                        .endDate(DateUtil.stringToLocalDateTimeConverter("2025-12-31"))
                        .fileName("fake_main_banner0"+i+".png")
                        .build();

                Advertisement advertisement2 = Advertisement.builder()
                        .type(Advertisement.Type.BOARD)
                        .url("https://github.com/DevCHW/gukmo-code-refactor")
                        .startDate(DateUtil.stringToLocalDateTimeConverter("2023-01-01"))
                        .endDate(DateUtil.stringToLocalDateTimeConverter("2025-12-31"))
                        .fileName("fake_main_banner0"+i+".png")
                        .build();
                em.persist(advertisement1);
                em.persist(advertisement2);
            }
        }
    }
}

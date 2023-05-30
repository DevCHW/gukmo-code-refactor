package com.devchw.gukmo.admin.repository;


import com.devchw.gukmo.admin.repository.custom.AdminMemberRepositoryCustom;
import com.devchw.gukmo.entity.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminMemberRepository extends JpaRepository<Member, Long>, AdminMemberRepositoryCustom {
    Long countByJoinDateBetween(LocalDateTime startDatetime, LocalDateTime endDatetime);

    @Query(value="select nvl(count,0) count" +
                 " from" +
                 " (" +
                 " select " +
                 "   count(*) as count, " +
                 "   to_char(join_date, 'yyyy-mm') as monthlydata" +
                 " from" +
                 "   member " +
                 " where 1=1" +
                 "   and join_date between to_date(to_char(sysdate,'yyyy')||'-01-01') and to_date(to_char(sysdate,'yyyy')||'-12-31')" +
                 " group by to_char(join_date, 'yyyy-mm')" +
                 " ) a" +
                 " right join" +
                 " (" +
                 " select to_char(add_months(to_date(to_char(sysdate,'yyyy')||'-12-31'),- (level-1)),'yyyy-mm') monthlydata" +
                 " from dual" +
                 " connect by add_months(to_date(to_char(sysdate,'yyyy')||'-12-31'),- (level-1)) >= add_months(to_date(to_char(sysdate,'yyyy')||'-12-31'),-11)" +
                 " ) b" +
                 " on a.monthlydata = b.monthlydata" +
                 " order by b.monthlydata", nativeQuery = true)
    List<Long> findIncreaseStats();

    @EntityGraph(attributePaths = {"academyMember", "login"})
    Optional<Member> findByNickname(String nickname);
}

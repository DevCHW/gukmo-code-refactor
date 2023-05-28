package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.admin.dto.board.CommunityActivityStatsQueryDto;
import com.devchw.gukmo.admin.repository.custom.AdminBoardRepositoryCustom;
import com.devchw.gukmo.entity.board.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminBoardRepository extends JpaRepository<Board, Long>, AdminBoardRepositoryCustom {

    @EntityGraph(attributePaths = {"member"})
    List<Board> findTop3ByFirstCategoryAndWriteDateBetweenOrderByViewsDesc(String firstCategory, LocalDateTime startDatetime, LocalDateTime endDatetime);

    Long countByWriteDateBetween(LocalDateTime startDatetime, LocalDateTime endDatetime);

    @Query(value="select round(cnt_free/cnt_all*100) freePercentage," +
            "           round(cnt_qna/cnt_all*100) qnaPercentage," +
            "           round(cnt_study/cnt_all*100) studyPercentage," +
            "           round(cnt_hobby/cnt_all*100) hobbyPercentage," +
            "           round(cnt_review/cnt_all*100) reviewPercentage" +
            "    from" +
            "    (" +
            "        select count(*) as cnt_free" +
            "        from board " +
            "        where write_date between to_date(to_char(sysdate,'yyyy-mm-dd') || ' 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate,'yyyy-mm-dd') || ' 23:59:59','yyyy-mm-dd hh24:mi:ss')" +
            "              and first_category='커뮤니티' and second_category = '자유'" +
            "    ) A" +
            "    cross join" +
            "    (" +
            "        select count(*) as cnt_qna" +
            "        from board " +
            "        where write_date between to_date(to_char(sysdate,'yyyy-mm-dd') || ' 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate,'yyyy-mm-dd') || ' 23:59:59','yyyy-mm-dd hh24:mi:ss')" +
            "              and first_category='커뮤니티' and second_category = 'QnA'" +
            "    ) B " +
            "    cross join" +
            "    (" +
            "        select count(*) as cnt_study" +
            "        from board " +
            "        where write_date between to_date(to_char(sysdate,'yyyy-mm-dd') || ' 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate,'yyyy-mm-dd') || ' 23:59:59','yyyy-mm-dd hh24:mi:ss')" +
            "              and first_category='커뮤니티' and second_category = '스터디'" +
            "    ) C" +
            "    cross join" +
            "    (" +
            "        select count(*) as cnt_hobby" +
            "        from board " +
            "        where write_date between to_date(to_char(sysdate,'yyyy-mm-dd') || ' 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate,'yyyy-mm-dd') || ' 23:59:59','yyyy-mm-dd hh24:mi:ss')" +
            "              and first_category='커뮤니티' and second_category = '취미모임'" +
            "    ) D" +
            "    cross join" +
            "    (" +
            "        select count(*) as cnt_review" +
            "        from board " +
            "        where write_date between to_date(to_char(sysdate,'yyyy-mm-dd') || ' 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate,'yyyy-mm-dd') || ' 23:59:59','yyyy-mm-dd hh24:mi:ss')" +
            "              and first_category='커뮤니티' and second_category = '수강/취업후기'" +
            "    ) E" +
            "    cross join" +
            "    (" +
            "        select case count(*) when 0 then 1 else count(*) end as cnt_all" +
            "        from board " +
            "        where write_date between to_date(to_char(sysdate,'yyyy-mm-dd') || ' 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate,'yyyy-mm-dd') || ' 23:59:59','yyyy-mm-dd hh24:mi:ss')" +
            "              and first_category='커뮤니티'" +
            "    ) F", nativeQuery = true)
    CommunityActivityStatsQueryDto findCommunityActivityStats();
}

package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.devchw.gukmo.entity.member.Activity.*;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @EntityGraph(attributePaths = {"member", "board"})
    Activity findByMemberIdAndBoardIdAndDivision(Long memberId, Long boardId, Division division);

    @EntityGraph(attributePaths = {"comments", "member"})
    Activity findWithCommentsWithMemberByCommentsIdAndMemberId(Long id, Long memberId);

    @EntityGraph(attributePaths = {"member"})
    Page<Activity> findAllByMemberId(Long memberId, Pageable pageable);
}

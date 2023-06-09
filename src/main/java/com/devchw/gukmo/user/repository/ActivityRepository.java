package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.user.repository.custom.ActivityRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.devchw.gukmo.entity.member.Activity.*;

public interface ActivityRepository extends JpaRepository<Activity, Long>, ActivityRepositoryCustom {

    @EntityGraph(attributePaths = {"member", "board"})
    Activity findByMemberIdAndBoardIdAndDivision(Long memberId, Long boardId, Division division);

    Optional<Activity> findByMemberIdAndCommentsIdAndDivision(Long memberId, Long commentsId, Division commentLike);
}

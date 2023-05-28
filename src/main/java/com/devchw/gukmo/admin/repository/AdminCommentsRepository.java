package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.entity.comment.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCommentsRepository extends JpaRepository<Comments, Long> {
}

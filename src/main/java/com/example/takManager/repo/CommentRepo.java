package com.example.takManager.repo;

import com.example.takManager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface CommentRepo extends JpaRepository<Comment, Long>,
        JpaSpecificationExecutor<Comment> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment WHERE task_id = ?1", nativeQuery = true)
    void deleteByTaskId(Long taskId);
}

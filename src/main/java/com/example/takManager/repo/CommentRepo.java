package com.example.takManager.repo;

import com.example.takManager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface CommentRepo extends JpaRepository<Comment, Long>,
        JpaSpecificationExecutor<Comment> {
    @Query(value = "DELETE FROM comment WHERE task_id = ?1", nativeQuery = true)
    void deleteByTaskId(Long taskId);
}

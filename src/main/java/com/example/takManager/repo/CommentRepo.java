package com.example.takManager.repo;

import com.example.takManager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE task_id = ?1", nativeQuery = true)
    List<Comment> findByTaskId(long taskId);
}

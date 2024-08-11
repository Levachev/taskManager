package com.example.taskManager.repo;

import com.example.taskManager.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>,
        JpaSpecificationExecutor<Comment> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comment WHERE task_id = ?1", nativeQuery = true)
    void deleteByTaskId(Long taskId);
}

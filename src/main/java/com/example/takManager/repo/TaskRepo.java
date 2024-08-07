package com.example.takManager.repo;

import com.example.takManager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;


public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM comment WHERE author_id = ?1", nativeQuery = true)
    Page<Task> findByAuthorId(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE author_id = ?1", nativeQuery = true)
    Page<Task> findByPerformerId(Long id, Pageable pageable);
}

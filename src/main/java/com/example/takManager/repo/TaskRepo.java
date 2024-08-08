package com.example.takManager.repo;

import com.example.takManager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}

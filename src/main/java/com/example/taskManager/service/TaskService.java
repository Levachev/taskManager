package com.example.taskManager.service;

import com.example.taskManager.dto.InputTaskDto;
import com.example.taskManager.dto.TaskDto;
import com.example.taskManager.spec.filter.TaskFilter;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasksByFilter(int page, TaskFilter filter);

    TaskDto getTaskById(Long id);

    TaskDto createTask(InputTaskDto inputTask);

    TaskDto updateTaskById(Long taskId, InputTaskDto inputTask);

    void deleteTaskById(Long taskId);

    TaskDto setPerformer(Long taskId, Long performerId);

    TaskDto setStatus(Long taskId, String status);
}

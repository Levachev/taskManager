package com.example.takManager.service;

import com.example.takManager.dto.InputTaskDto;
import com.example.takManager.dto.TaskDto;
import com.example.takManager.entity.Task;
import com.example.takManager.mapper.TaskMapper;
import com.example.takManager.model.Status;
import com.example.takManager.repo.CommentRepo;
import com.example.takManager.repo.TaskRepo;
import com.example.takManager.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;

    public List<TaskDto> getAllTasksByAuthorId(Long authorId, int page) {
        int pageNumber = page < 1 ? 0 : page-1;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return taskRepo.findByAuthorId(authorId, pageable).getContent().stream()
                .map(task -> TaskMapper.toTaskDto(task, commentRepo.findByTaskId(task.getId())))
                .collect(Collectors.toList());
    }

    public List<TaskDto> getAllTasksByPerformerId(Long performerId, int page) {
        int pageNumber = page < 1 ? 0 : page-1;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return taskRepo.findByPerformerId(performerId, pageable).getContent().stream()
                .map(task -> TaskMapper.toTaskDto(task, commentRepo.findByTaskId(task.getId())))
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepo.getReferenceById(id);
        return TaskMapper.toTaskDto(task, commentRepo.findByTaskId(task.getId()));
    }

    public void createTask(InputTaskDto inputTask) {
        Task task = Task.builder()
                .author(userRepo.getReferenceById(inputTask.authorId()))
                .title(inputTask.title())
                .description(inputTask.description())
                .status(inputTask.status())
                .priority(inputTask.priority())
                .performer(
                        inputTask.performerId() == null
                                ? null : userRepo.getReferenceById(inputTask.performerId())
                )
                .build();
        taskRepo.save(task);
    }

    public void updateTaskById(Long id, InputTaskDto inputTask) {
        Task task = Task.builder()
                .id(id)
                .author(userRepo.getReferenceById(inputTask.authorId()))
                .title(inputTask.title())
                .description(inputTask.description())
                .status(inputTask.status())
                .priority(inputTask.priority())
                .performer(
                        inputTask.performerId() == null
                                ? null : userRepo.getReferenceById(inputTask.performerId())
                )
                .build();
        taskRepo.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepo.deleteById(id);
    }

    public void setPerformer(Long taskId, Long performerId) {
        var task = taskRepo.getReferenceById(taskId);
        task.setPerformer(userRepo.getReferenceById(performerId));
        taskRepo.save(task);
    }

    public void setStatus(Long id, String status) {
        var task = taskRepo.getReferenceById(id);
        task.setStatus(Status.valueOf(status));
        taskRepo.save(task);
    }
}

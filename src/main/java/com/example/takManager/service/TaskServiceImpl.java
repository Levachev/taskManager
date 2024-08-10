package com.example.takManager.service;

import com.example.takManager.dto.InputTaskDto;
import com.example.takManager.dto.TaskDto;
import com.example.takManager.entity.Task;
import com.example.takManager.entity.User;
import com.example.takManager.exception.UnauthorizedAccessException;
import com.example.takManager.exception.UserNotFoundException;
import com.example.takManager.mapper.TaskMapper;
import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;
import com.example.takManager.repo.TaskRepo;
import com.example.takManager.repo.UserRepo;
import com.example.takManager.spec.TaskSpec;
import com.example.takManager.spec.filter.TaskFilter;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final CommentServiceImpl commentService;

    public List<TaskDto> getAllTasksByFilter(int page, TaskFilter filter) {
        int pageNumber = page < 1 ? 0 : page-1;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Task> specification = TaskSpec.filterBy(filter);

        return taskRepo.findAll(specification, pageable).getContent().stream()
                .map(TaskMapper::toTaskDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepo.getReferenceById(id);
        return TaskMapper.toTaskDto(task);
    }

    public TaskDto createTask(InputTaskDto inputTask) {
        Task task = Task.builder()
                .author(getCurrentUser())
                .title(inputTask.getTitle())
                .description(inputTask.getDescription())
                .status(Status.valueOf(inputTask.getStatus()))
                .priority(Priority.valueOf(inputTask.getPriority()))
                .performer(
                        inputTask.getPerformerId() == null
                                ? null : userRepo.getReferenceById(inputTask.getPerformerId())
                )
                .build();
        return TaskMapper.toTaskDto(taskRepo.save(task));
    }

    public TaskDto updateTaskById(Long taskId, InputTaskDto inputTask) {
        User currentUser = getCurrentUser();
        Task oldVerTask = taskRepo.getReferenceById(taskId);
        if(!Objects.equals(oldVerTask.getAuthor().getId(), currentUser.getId())){
            throw new UnauthorizedAccessException("only author can update task");
        }

        Task task = Task.builder()
                .id(taskId)
                .author(currentUser)
                .title(inputTask.getTitle())
                .description(inputTask.getDescription())
                .status(Status.valueOf(inputTask.getStatus()))
                .priority(Priority.valueOf(inputTask.getPriority()))
                .performer(
                        inputTask.getPerformerId() == null
                                ? null : userRepo.getReferenceById(inputTask.getPerformerId())
                )
                .build();
        return TaskMapper.toTaskDto(taskRepo.save(task));
    }

    public void deleteTaskById(Long taskId) {
        User currentUser = getCurrentUser();
        Task oldVerTask = taskRepo.getReferenceById(taskId);
        if(!Objects.equals(oldVerTask.getAuthor().getId(), currentUser.getId())){
            throw new UnauthorizedAccessException("only author can delete task");
        }

        commentService.deleteCommentsByTaskId(taskId);
        taskRepo.deleteById(taskId);
    }

    public TaskDto setPerformer(Long taskId, Long performerId)  {
        User currentUser = getCurrentUser();
        Task oldVerTask = taskRepo.getReferenceById(taskId);
        if(!Objects.equals(oldVerTask.getAuthor().getId(), currentUser.getId())){
            throw new UnauthorizedAccessException("only author can set performer to task");
        }

        oldVerTask.setPerformer(userRepo.getReferenceById(performerId));
        return TaskMapper.toTaskDto(taskRepo.save(oldVerTask));
    }

    public TaskDto setStatus(Long taskId, String status)  {
        User currentUser = getCurrentUser();
        Task oldVerTask = taskRepo.getReferenceById(taskId);
        if(!Objects.equals(oldVerTask.getAuthor().getId(), currentUser.getId())
            || !Objects.equals(oldVerTask.getPerformer().getId(), currentUser.getId())){
            throw new UnauthorizedAccessException("only author and performer change status of task");
        }

        oldVerTask.setStatus(Status.valueOf(status));
        return TaskMapper.toTaskDto(taskRepo.save(oldVerTask));
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new UserNotFoundException(
                        "cannot find current user in data base, name is - "+userDetails.getUsername()
                )
        );
    }
}

package com.example.takManager.controller;


import com.example.takManager.dto.InputTaskDto;
import com.example.takManager.dto.TaskDto;
import com.example.takManager.service.TaskServiceImpl;
import com.example.takManager.spec.filter.TaskFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Tag(name = "task operation api")
public class TaskController {
    private final TaskServiceImpl taskService;


    @Operation(summary = "get all tasks by author id")
    @GetMapping("/get-all/author/{id}")
    public List<TaskDto> getAllTasksByAuthorId(@PathVariable Long authorId,
                                               @RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, value = "title_part") String titlePart,
                                               @RequestParam(required = false, value = "description_part") String descriptionPart,
                                               @RequestParam(required = false, value = "priority_part") String priorityPart,
                                               @RequestParam(required = false, value = "status_part") String statusPart,
                                               @RequestParam(required = false, value = "performer_id") Long performerId){
        return taskService.getAllTasksByFilter(page,
                new TaskFilter(authorId, titlePart, descriptionPart, priorityPart, statusPart, performerId));
    }


    @Operation(summary = "get all tasks by performer id")
    @GetMapping("/get-all/performer/{id}")
    public List<TaskDto> getAllTasksByPerformerId(@PathVariable Long performerId,
                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, value = "title_part") String titlePart,
                                                  @RequestParam(required = false, value = "description_part") String descriptionPart,
                                                  @RequestParam(required = false, value = "priority_part") String priorityPart,
                                                  @RequestParam(required = false, value = "status_part") String statusPart,
                                                  @RequestParam(required = false, value = "author_id") Long authorId){
        return taskService.getAllTasksByFilter(page,
                new TaskFilter(authorId, titlePart, descriptionPart, priorityPart, statusPart, performerId));
    }


    @Operation(summary = "get task by id")
    @GetMapping("/get/{id}")
    public TaskDto getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }


    @Operation(summary = "create task")
    @PostMapping()
    public void createTask(@RequestBody InputTaskDto task){
        taskService.createTask(task);
    }


    @Operation(summary = "update task by id")
    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody InputTaskDto task){
        taskService.updateTaskById(id, task);
    }


    @Operation(summary = "delete task by id")
    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }


    @Operation(summary = "update performer of task by task id")
    @PatchMapping("/{id}/performer")
    public void setPerformer(@PathVariable Long id, @RequestBody Long performerId){
        taskService.setPerformer(id, performerId);
    }


    @Operation(summary = "update status of task by task id")
    @PatchMapping("/{id}/status")
    public void setStatus(@PathVariable Long id, @RequestBody String status){
        taskService.setStatus(id, status);
    }
}

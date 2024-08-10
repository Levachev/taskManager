package com.example.takManager.controller;


import com.example.takManager.dto.InputPerformerDto;
import com.example.takManager.dto.InputTaskDto;
import com.example.takManager.dto.StatusDto;
import com.example.takManager.dto.TaskDto;
import com.example.takManager.service.TaskServiceImpl;
import com.example.takManager.spec.filter.TaskFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    public List<TaskDto> getAllTasksByAuthorId(@PathVariable@Parameter(description = "author id") Long authorId,
               @Min(0) @RequestParam(required = false, defaultValue = "0")@Parameter(description = "page number") int page,
               @RequestParam(required = false, value = "title_part")@Parameter(description = "title part") String titlePart,
               @RequestParam(required = false, value = "description_part")@Parameter(description = "description part") String descriptionPart,
               @RequestParam(required = false, value = "priority")@Parameter(description = "priority") String priority,
               @RequestParam(required = false, value = "status")@Parameter(description = "status") String status,
               @RequestParam(required = false, value = "performer_id")@Parameter(description = "performer id") Long performerId){
        return taskService.getAllTasksByFilter(page,
                new TaskFilter(authorId, titlePart, descriptionPart, priority, status, performerId));
    }


    @Operation(summary = "get all tasks by performer id")
    @GetMapping("/get-all/performer/{id}")
    public List<TaskDto> getAllTasksByPerformerId(@PathVariable@Parameter(description = "performer id") Long performerId,
              @Min(0) @RequestParam(required = false, defaultValue = "0")@Parameter(description = "page number") int page,
              @RequestParam(required = false, value = "title_part")@Parameter(description = "title part") String titlePart,
              @RequestParam(required = false, value = "description_part")@Parameter(description = "description part") String descriptionPart,
              @RequestParam(required = false, value = "priority")@Parameter(description = "priority") String priority,
              @RequestParam(required = false, value = "status")@Parameter(description = "status") String status,
              @RequestParam(required = false, value = "author_id")@Parameter(description = "author id") Long authorId){
        return taskService.getAllTasksByFilter(page,
                new TaskFilter(authorId, titlePart, descriptionPart, priority, status, performerId));
    }


    @Operation(summary = "get task by id")
    @GetMapping("/get/{id}")
    public TaskDto getTaskById(@PathVariable@Parameter(description = "task id") Long id){
        return taskService.getTaskById(id);
    }


    @Operation(summary = "create task")
    @PostMapping()
    public void createTask(@RequestBody @Valid InputTaskDto task){
        taskService.createTask(task);
    }


    @Operation(summary = "update task by id")
    @PutMapping("/{id}")
    public void updateTask(@PathVariable@Parameter(description = "task id") Long id, @RequestBody @Valid InputTaskDto task){
        taskService.updateTaskById(id, task);
    }


    @Operation(summary = "delete task by id")
    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable@Parameter(description = "task id") Long id){
        taskService.deleteTaskById(id);
    }


    @Operation(summary = "update performer of task by task id")
    @PatchMapping("/{id}/performer")
    public void setPerformer(@PathVariable@Parameter(description = "task id") Long id,
                             @RequestBody InputPerformerDto performerDto){
        taskService.setPerformer(id, performerDto.getPerformerId());
    }


    @Operation(summary = "update status of task by task id")
    @PatchMapping("/{id}/status")
    public void setStatus(@PathVariable@Parameter(description = "task id") Long id,
                          @RequestBody StatusDto statusDto){
        taskService.setStatus(id, statusDto.getStatus());
    }
}

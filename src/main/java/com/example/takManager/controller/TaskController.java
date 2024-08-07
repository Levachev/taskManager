package com.example.takManager.controller;


import com.example.takManager.dto.InputTaskDto;
import com.example.takManager.dto.TaskDto;
import com.example.takManager.service.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskServiceImpl taskService;


    @GetMapping("/get-all/author/{id}")
    public List<TaskDto> getAllTasksByAuthorId(@PathVariable Long authorId,
            @RequestParam(required = false, defaultValue = "0") int page){
        return taskService.getAllTasksByAuthorId(authorId, page);
    }

    @GetMapping("/get-all/performer/{id}")
    public List<TaskDto> getAllTasksByPerformerId(@PathVariable Long performerId,
                                     @RequestParam(required = false, defaultValue = "0") int page){
        return taskService.getAllTasksByPerformerId(performerId, page);
    }

    @GetMapping("/get/{id}")
    public TaskDto getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PostMapping()
    public void createTask(@RequestBody InputTaskDto task){
        taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody InputTaskDto task){
        taskService.updateTaskById(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }

    @PatchMapping("/{id}/performer")
    public void setPerformer(@PathVariable Long id, @RequestBody Long performerId){
        taskService.setPerformer(id, performerId);
    }

    @PatchMapping("/{id}/status")
    public void setStatus(@PathVariable Long id, @RequestBody String status){
        taskService.setStatus(id, status);
    }
}

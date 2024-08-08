package com.example.takManager.mapper;

import com.example.takManager.dto.TaskDto;
import com.example.takManager.entity.Task;

public class TaskMapper {
    public static TaskDto toTaskDto(Task task){
        if(task == null){
            return null;
        }

        return new TaskDto(task.getId(), task.getAuthor(), task.getTitle(),
                task.getDescription(), task.getPriority(), task.getStatus(),
                task.getPerformer());
    }
}

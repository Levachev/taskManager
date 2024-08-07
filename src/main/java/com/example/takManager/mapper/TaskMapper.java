package com.example.takManager.mapper;

import com.example.takManager.dto.TaskDto;
import com.example.takManager.entity.Comment;
import com.example.takManager.entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    public static TaskDto toTaskDto(Task task, List<Comment> comments){
        if(task == null){
            return null;
        }

        return new TaskDto(task.getId(), task.getTitle(),
                task.getDescription(), task.getPriority(), task.getStatus(),
                task.getPerformer(),
                comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList()));
    }
}

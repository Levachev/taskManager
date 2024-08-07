package com.example.takManager.dto;

import com.example.takManager.entity.User;
import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;

import java.util.List;

public record TaskDto(
    Long id,
    String title,
    String description,
    Priority priority,
    Status status,
    User performer,
    List<CommentDto> comments){

}

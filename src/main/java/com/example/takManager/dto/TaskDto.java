package com.example.takManager.dto;

import com.example.takManager.entity.User;
import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "output task format")
public record TaskDto(
    Long id,
    User author,
    String title,
    String description,
    Priority priority,
    Status status,
    User performer){
}

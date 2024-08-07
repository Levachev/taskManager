package com.example.takManager.dto;

import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;

public record InputTaskDto(
        Long authorId,
        String title,
        String description,
        Priority priority,
        Status status,
        Long performerId
) {
}

package com.example.taskManager.spec.filter;


public record TaskFilter(
        Long authorId,
        String title,
        String description,
        String priority,
        String status,
        Long performerId
) {
}

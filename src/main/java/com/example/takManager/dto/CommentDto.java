package com.example.takManager.dto;

public record CommentDto(
        Long taskId,
        String comment,
        Long commentatorId
) {
}

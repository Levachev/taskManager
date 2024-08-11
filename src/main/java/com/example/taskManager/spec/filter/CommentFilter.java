package com.example.taskManager.spec.filter;

public record CommentFilter(
        Long taskId,
        String comment,
        Long commentatorId
) {
}

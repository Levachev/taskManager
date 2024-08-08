package com.example.takManager.spec.filter;

public record CommentFilter(
        Long taskId,
        String comment,
        Long commentatorId
) {
}

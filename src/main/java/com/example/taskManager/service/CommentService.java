package com.example.taskManager.service;

import com.example.taskManager.dto.CommentDto;
import com.example.taskManager.spec.filter.CommentFilter;

import java.util.List;

public interface CommentService {
    List<CommentDto> getByTaskId(int page, CommentFilter filter);

    Long addComment(CommentDto commentDto);

    void deleteCommentsByTaskId(Long taskId);
}

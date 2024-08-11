package com.example.taskManager.mapper;

import com.example.taskManager.dto.CommentDto;
import com.example.taskManager.entity.Comment;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment){
        if(comment == null){
            return null;
        }

        return new CommentDto(comment.getTask().getId(), comment.getComment());
    }
}

package com.example.takManager.mapper;

import com.example.takManager.dto.CommentDto;
import com.example.takManager.entity.Comment;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment){
        if(comment == null){
            return null;
        }

        return new CommentDto(comment.getTask().getId(), comment.getComment());
    }
}

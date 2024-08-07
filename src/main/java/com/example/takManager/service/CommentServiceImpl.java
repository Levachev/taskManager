package com.example.takManager.service;

import com.example.takManager.dto.CommentDto;
import com.example.takManager.entity.Comment;
import com.example.takManager.repo.CommentRepo;
import com.example.takManager.repo.TaskRepo;
import com.example.takManager.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

    public List<Comment> getByTaskId(Long taskId){
       return commentRepo.findByTaskId(taskId);
    }

    public void addComment(CommentDto commentDto){
        Comment comment = Comment.builder()
                .task(taskRepo.getReferenceById(commentDto.taskId()))
                .commentator(userRepo.getReferenceById(commentDto.commentatorId()))
                .comment(commentDto.content())
                .build();

        commentRepo.save(comment);
    }
}

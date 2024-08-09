package com.example.takManager.service;

import com.example.takManager.dto.CommentDto;
import com.example.takManager.entity.Comment;
import com.example.takManager.mapper.CommentMapper;
import com.example.takManager.repo.CommentRepo;
import com.example.takManager.repo.TaskRepo;
import com.example.takManager.repo.UserRepo;
import com.example.takManager.spec.CommentSpec;
import com.example.takManager.spec.filter.CommentFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

    public List<CommentDto> getByTaskId(int page, CommentFilter filter){
        int pageNumber = page < 1 ? 0 : page-1;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Comment> specification = CommentSpec.filterBy(filter);

        return commentRepo.findAll(specification, pageable).getContent().stream()
               .map(CommentMapper::toCommentDto)
               .collect(Collectors.toList());
    }

    public void addComment(CommentDto commentDto){
        Comment comment = Comment.builder()
                .task(taskRepo.getReferenceById(commentDto.getTaskId()))
                .commentator(userRepo.getReferenceById(commentDto.getCommentatorId()))
                .comment(commentDto.getComment())
                .build();

        commentRepo.save(comment);
    }

    public void deleteCommentsByTaskId(Long taskId){
        commentRepo.deleteByTaskId(taskId);
    }
}

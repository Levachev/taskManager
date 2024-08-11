package com.example.taskManager.service;

import com.example.taskManager.dto.CommentDto;
import com.example.taskManager.entity.Comment;
import com.example.taskManager.entity.User;
import com.example.taskManager.exception.UserNotFoundException;
import com.example.taskManager.mapper.CommentMapper;
import com.example.taskManager.repo.CommentRepo;
import com.example.taskManager.repo.TaskRepo;
import com.example.taskManager.repo.UserRepo;
import com.example.taskManager.spec.CommentSpec;
import com.example.taskManager.spec.filter.CommentFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

    @Override
    public List<CommentDto> getByTaskId(int page, CommentFilter filter){
        int pageNumber = page < 1 ? 0 : page-1;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Comment> specification = CommentSpec.filterBy(filter);

        return commentRepo.findAll(specification, pageable).getContent().stream()
               .map(CommentMapper::toCommentDto)
               .collect(Collectors.toList());
    }

    @Override
    public Long addComment(CommentDto commentDto){
        Comment comment = Comment.builder()
                .task(taskRepo.getReferenceById(commentDto.getTaskId()))
                .commentator(getCurrentUser())
                .comment(commentDto.getComment())
                .build();

        return commentRepo.save(comment).getId();
    }

    @Override
    public void deleteCommentsByTaskId(Long taskId){
        commentRepo.deleteByTaskId(taskId);
    }

    User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new UserNotFoundException(
                        "cannot find current user in data base, name is - "+userDetails.getUsername()
                )
        );
    }

}

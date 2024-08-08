package com.example.takManager.controller;

import com.example.takManager.dto.CommentDto;
import com.example.takManager.service.CommentServiceImpl;
import com.example.takManager.spec.filter.CommentFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "comment operation api")
public class CommentController {
    private final CommentServiceImpl commentService;


    @Operation(summary = "add comment to task")
    @PostMapping("/add")
    public void comment(@RequestBody CommentDto commentDto){
        commentService.addComment(commentDto);
    }


    @Operation(summary = "get all comments by task id")
    @GetMapping("/get/task/{id}")
    public List<CommentDto> getCommentsByTask(@RequestParam(required = false, defaultValue = "0") int page,
                                              @PathVariable Long taskId,
                                              @RequestParam(required = false, value = "comment_part") String commentPart,
                                              @RequestParam(required = false, value = "commentator_id") Long commentatorId){
        return commentService.getByTaskId(page,
                new CommentFilter(taskId, commentPart, commentatorId));
    }
}

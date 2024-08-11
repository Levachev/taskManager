package com.example.taskManager.controller;

import com.example.taskManager.dto.CommentDto;
import com.example.taskManager.service.CommentService;
import com.example.taskManager.spec.filter.CommentFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "comment operation api")
public class CommentController {
    private final CommentService commentService;


    @Operation(summary = "add comment to task", responses = {@ApiResponse(
            responseCode = "200",
            description = "Id of comment",
            content = {
                    @Content(
                            mediaType = "application/json"
                    )
            })
    })
    @PostMapping()
    public Long comment(@RequestBody @Valid CommentDto commentDto){
        return commentService.addComment(commentDto);
    }


    @Operation(summary = "get all comments by task id")
    @GetMapping("/{id}")
    public List<CommentDto> getCommentsByTask(@RequestParam(required = false, defaultValue = "0")@Parameter(description = "page number") int page,
                                              @PathVariable(value = "id")@Parameter(description = "task id") Long taskId,
                                              @RequestParam(required = false, value = "comment_part")@Parameter(description = "comment part") String commentPart,
                                              @RequestParam(required = false, value = "commentator_id")@Parameter(description = "performer id") Long commentatorId){
        return commentService.getByTaskId(page,
                new CommentFilter(taskId, commentPart, commentatorId));
    }
}

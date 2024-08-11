package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "output task format")
public class TaskDto{
    @Schema(description = "task id")
    private Long id;

    @Schema(description = "author id")
    private Long authorId;

    @Schema(description = "author email")
    private String authorEmail;

    @Schema(description = "title")
    private String title;

    @Schema(description = "description")
    private String description;

    @Schema(description = "priority")
    private String priority;

    @Schema(description = "status")
    private String status;

    @Schema(description = "performer id")
    private Long performerId;

    @Schema(description = "performer email")
    private String performerEmail;
}

package com.example.takManager.dto;

import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;
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
    private Priority priority;

    @Schema(description = "status")
    private Status status;

    @Schema(description = "performer id")
    private Long performerId;

    @Schema(description = "performer email")
    private String performerEmail;
}

package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "output comment format")
public class CommentDto{
    @NotNull(message = "task id cannot be blank")
    @Schema(description = "task id")
    private Long taskId;

    @NotBlank(message = "comment cannot be blank")
    @Schema(description = "comment")
    private String comment;
}

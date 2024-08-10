package com.example.takManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

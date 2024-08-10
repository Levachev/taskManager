package com.example.takManager.dto;

import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "input task format")
public class InputTaskDto{

    @NotBlank(message = "title cannot be blank")
    @Schema(description = "title")
    private String title;

    @NotBlank(message = "description cannot be blank")
    @Schema(description = "description")
    private String description;

    @NotBlank(message = "priority cannot be blank")
    @Schema(description = "priority")
    private Priority priority;

    @NotBlank(message = "status cannot be blank")
    @Schema(description = "status")
    private Status status;

    @Schema(description = "performer id")
    private Long performerId;
}

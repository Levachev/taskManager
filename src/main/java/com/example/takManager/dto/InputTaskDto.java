package com.example.takManager.dto;

import com.example.takManager.model.Priority;
import com.example.takManager.model.Status;
import com.example.takManager.valid.EnumValidator;
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

    @EnumValidator(
            enumClazz = Priority.class,
            message = "This error is coming from the priority enum class"
    )
    @Schema(description = "priority: LOW, MEDIUM, HIGH", example = "LOW")
    private String priority;

    @EnumValidator(
            enumClazz = Status.class,
            message = "This error is coming from the status enum class"
    )
    @Schema(description = "status: PENDING, IN_PROCESS, COMPLETE", example = "PENDING")
    private String status;

    @Schema(description = "performer id")
    private Long performerId;
}

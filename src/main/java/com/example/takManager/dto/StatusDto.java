package com.example.takManager.dto;


import com.example.takManager.model.Status;
import com.example.takManager.valid.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "input task format")
public class StatusDto {
    @EnumValidator(
            enumClazz = Status.class,
            message = "This error is coming from the status enum class"
    )
    @Schema(description = "status: PENDING, IN_PROCESS, COMPLETE", example = "IN_PROGRESS")
    private String status;
}

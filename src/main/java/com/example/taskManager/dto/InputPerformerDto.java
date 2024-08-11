package com.example.taskManager.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "input performer format")
public class InputPerformerDto {
    @NotNull
    @Schema(description = "performer id")
    private Long performerId;
}

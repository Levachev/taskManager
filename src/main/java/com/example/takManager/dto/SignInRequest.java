package com.example.takManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Authentication request")
public class SignInRequest {

    @Schema(description = "user email", example = "Jon@gmail.com")
    @Size(min = 5, max = 255, message = "email must contain from 5 to 255 characters")
    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "email must be in email format")
    private String email;

    @Schema(description = "password", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Password length must be from 8 to 255 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}

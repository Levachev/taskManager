package com.example.takManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Registration Request")
public class SignUpRequest {

    @Schema(description = "user email", example = "Jon@gmail.com")
    @Size(min = 5, max = 255, message = "email must contain from 5 to 255 characters")
    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "email must be in email format")
    private String email;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Password length must be no more than 255 characters")
    private String password;
}

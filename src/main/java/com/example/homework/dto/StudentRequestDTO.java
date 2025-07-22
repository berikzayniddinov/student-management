package com.example.homework.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequestDTO {
    @NotBlank(message = "First name must not be empty")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @Min(value = 0, message = "Age must be at least 0")
    private int age;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private LocalDate dateOfBirth;
}

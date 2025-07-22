package com.example.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private LocalDate dateOfBirth;
}
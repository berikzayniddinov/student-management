package com.example.homework.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class University {
    private List<Student> students = new ArrayList<>();
}
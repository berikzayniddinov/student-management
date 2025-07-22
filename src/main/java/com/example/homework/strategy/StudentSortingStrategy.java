package com.example.homework.strategy;

import com.example.homework.model.Student;

import java.util.List;

public interface StudentSortingStrategy {
    List<Student> sort(List<Student> students);
}
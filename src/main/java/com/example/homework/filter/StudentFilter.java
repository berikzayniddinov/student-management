package com.example.homework.filter;

import com.example.homework.model.Student;

@FunctionalInterface
public interface StudentFilter {
    boolean filter(Student student);
}
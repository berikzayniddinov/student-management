package com.example.homework.strategy;

import com.example.homework.model.Student;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component("sortByName")
public class SortByNameStrategy implements StudentSortingStrategy {
    @Override
    public List<Student> sort(List<Student> students) {
        return students.stream()
                .sorted(Comparator.comparing(Student::getFirstName))
                .toList();
    }
}
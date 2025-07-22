package com.example.homework.service;

import com.example.homework.command.Command;
import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.filter.StudentFilter;
import com.example.homework.model.Student;
import com.example.homework.model.University;
import com.example.homework.strategy.StudentSortingStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("universityService")
@Qualifier("dbService")
@RequiredArgsConstructor
public class UniversityService {

    private final University university;
    private final StudentService dbService;

    @PostConstruct
    public void initUniversity() {
        dbService.findAllStudent(new StudentRequestDTO()).forEach(dto ->
                university.getStudents().add(Student.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .age(dto.getAge())
                        .dateOfBirth(dto.getDateOfBirth())
                        .build())
        );
    }

    public void executeCommand(Command command) {
        command.execute();
    }

    public List<Student> getFilteredStudents(StudentFilter filter) {
        return university.getStudents().stream()
                .filter(filter::filter)
                .toList();
    }

    public List<Student> getSortedStudents(StudentSortingStrategy strategy) {
        return strategy.sort(university.getStudents());
    }

    public void addStudent(Student student) {
        university.getStudents().add(student);
    }

    public void removeStudent(String email) {
        university.getStudents().removeIf(s -> s.getEmail().equalsIgnoreCase(email));
    }
}

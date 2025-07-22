package com.example.homework.controller;

import com.example.homework.command.DeleteStudentCommand;
import com.example.homework.command.SaveStudentCommand;
import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.filter.StudentFilter;
import com.example.homework.mapper.StudentMapper;
import com.example.homework.model.Student;
import com.example.homework.repository.StudentJpaRepository;
import com.example.homework.service.StudentService;
import com.example.homework.service.UniversityService;
import com.example.homework.strategy.SortingStrategyFactory;
import com.example.homework.strategy.StudentSortingStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;
    private final StudentJpaRepository studentRepository;
    private final StudentMapper studentMapper;

    @Qualifier("dbService")
    private final StudentService dbService;

    private final SortingStrategyFactory sortingStrategyFactory;

    @Operation(summary = "Add a new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student added successfully"),
            @ApiResponse(responseCode = "400", description = "Student with email already exists")
    })
    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Student with email already exists.");
        }
        StudentRequestDTO requestDTO = studentMapper.toRequestDto(student);

        universityService.executeCommand(new SaveStudentCommand(dbService, requestDTO));
        universityService.addStudent(student);
        return ResponseEntity.ok("Student added successfully.");
    }


    @Operation(summary = "Delete student by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted successfully")
    })
    @DeleteMapping("/delete/{email}")
    public void deleteStudent(@PathVariable String email) {
        universityService.executeCommand(new DeleteStudentCommand(dbService, email));
        universityService.removeStudent(email);
    }

    @Operation(summary = "Filter students older than given age")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered students retrieved successfully")
    })
    @GetMapping("/filter/ageGreaterThan/{age}")
    public List<Student> filterByAge(@PathVariable int age) {
        StudentFilter filter = student -> student.getAge() > age;
        return universityService.getFilteredStudents(filter);
    }

    @Operation(summary = "Sort students using chosen strategy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Students sorted successfully")
    })
    @GetMapping("/sort")
    public List<Student> sortStudents(@RequestParam(defaultValue = "sortByAge") String strategy) {
        StudentSortingStrategy sortingStrategy = sortingStrategyFactory.getStrategy(strategy);
        return universityService.getSortedStudents(sortingStrategy);
    }
}
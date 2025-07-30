package com.example.homework.controller;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.impl.DatabaseStudentServiceImpl;
import com.example.homework.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService dbService;

    public StudentController(@Qualifier("dbService") StudentService dbService) {
        this.dbService = dbService;
    }

    @Operation(summary = "Получение всех студентов")
    @GetMapping
    public List<StudentResponseDTO> findAllStudents() {
        return dbService.findAllStudent(new StudentRequestDTO());
    }

    @Operation(summary = "Сохранение нового студента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student успешно сохранён"),
            @ApiResponse(responseCode = "400", description = "Студент с таким email уже существует")
    })
    @PostMapping("/save")
    public ResponseEntity<?> saveStudent(@Valid @RequestBody StudentRequestDTO studentDTO) {
        if (dbService.emailExists(studentDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Student with email already exists.");
        }

        StudentResponseDTO savedStudent = dbService.saveStudent(studentDTO);
        return ResponseEntity.ok(savedStudent);
    }

    @Operation(summary = "Поиск по email")
    @GetMapping("/{email}")
    public StudentResponseDTO findByEmail(@PathVariable String email) {
        return dbService.findByEmail(email);
    }

    @Operation(summary = "Обновление студента")
    @PutMapping("/update")
    public StudentResponseDTO updateStudent(@Valid @RequestBody StudentRequestDTO studentDTO) {
        return dbService.updateStudent(studentDTO);
    }

    @Operation(summary = "Удаление студента по email")
    @DeleteMapping("/delete/{email}")
    public void deleteStudent(@PathVariable String email) {
        dbService.deleteStudent(email);
    }

    @Operation(summary = "Прямой поиск студента по email из БД")
    @GetMapping("/byEmail")
    public ResponseEntity<StudentResponseDTO> getByEmail(@RequestParam String email) {
        return dbService.getByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Студенты старше указанного возраста")
    @GetMapping("/ageGreaterThan/{age}")
    public List<StudentResponseDTO> getByAge(@PathVariable int age) {
        return dbService.getByAgeGreaterThan(age);
    }

    @Operation(summary = "Поиск по имени (JPQL)")
    @GetMapping("/firstName/{name}")
    public List<StudentResponseDTO> getByFirstName(@PathVariable String name) {
        return dbService.getByFirstName(name);
    }

    @Operation(summary = "Поиск по фамилии (Native SQL)")
    @GetMapping("/lastName/{name}")
    public List<StudentResponseDTO> getByLastName(@PathVariable String name) {
        return dbService.getByLastName(name);
    }

    @Operation(summary = "Постраничный вывод студентов")
    @GetMapping("/paged")
    public Page<StudentResponseDTO> getPagedStudents(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return dbService.getPagedStudents(page, size, sortBy, direction);
    }

    @Operation(summary = "Поиск студентов по названию книги")
    @GetMapping("/by-book-title")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBookTitle(@RequestParam String title) {
        if (dbService instanceof DatabaseStudentServiceImpl dbImpl) {
            return ResponseEntity.ok(dbImpl.getStudentsByBookTitle(title));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Получить студента с книгами по email")
    @GetMapping("/by-email-book")
    public ResponseEntity<StudentResponseDTO> getStudentWithBooksByEmail(@RequestParam String email) {
        if (dbService instanceof DatabaseStudentServiceImpl dbImpl) {
            return dbImpl.getStudentWithBooksByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(500).build();
    }

    @Operation(summary = "Поиск по названию книги, имени и email")
    @GetMapping("/by-book-title-name")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBookTitleAndNameAndEmail(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String email) {
        if (dbService instanceof DatabaseStudentServiceImpl dbImpl) {
            return ResponseEntity.ok(dbImpl.searchByBookTitleAndNameAndEmail(title, name, email));
        }
        return ResponseEntity.status(500).build();
    }
}
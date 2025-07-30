package com.example.homework.controller;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.impl.DatabaseStudentServiceImpl;
import com.example.homework.mapper.StudentMapper;
import com.example.homework.model.Student;
import com.example.homework.repository.StudentJpaRepository;
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

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService inMemoryService;
    private final StudentService dbService;
    private final StudentJpaRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentController(
            @Qualifier("inMemoryService") StudentService inMemoryService,
            @Qualifier("dbService") StudentService dbService,
            StudentJpaRepository studentRepository, StudentMapper studentMapper
    ) {
        this.inMemoryService = inMemoryService;
        this.dbService = dbService;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    private StudentService chooseService(String source) {
        return "db".equalsIgnoreCase(source) ? dbService : inMemoryService;
    }

    @Operation(
            summary = "Получение всех студентов",
            description = "Возвращает список всех студентов из выбранного источника данных (в памяти или база данных)."
    )
    @GetMapping
    public List<StudentResponseDTO> findAllStudents(
            @Parameter(description = "Data source: 'memory' or 'db'", example = "db")
            @RequestParam(defaultValue = "memory") String source
    ) {
        return chooseService(source).findAllStudent(new StudentRequestDTO());
    }

    @Operation(summary = "Save a new student", description = "Saves a new student to selected source.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student successfully saved"),
            @ApiResponse(responseCode = "400", description = "Student with email already exists")
    })
    @PostMapping("/save")
    public ResponseEntity<?> saveStudent(
            @Parameter(description = "Data source: 'memory' or 'db'", example = "db")
            @RequestParam(defaultValue = "memory") String source,
            @Valid @RequestBody StudentRequestDTO studentDTO) {

        if (studentRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Student with email already exists.");
        }

        StudentResponseDTO savedStudent = chooseService(source).saveStudent(studentDTO);
        return ResponseEntity.ok(savedStudent);
    }

    @Operation(
            summary = "Поиск студента по email",
            description = "Ищет и возвращает студента с указанным email из выбранного источника данных."
    )
    @GetMapping("/{email}")
    public StudentResponseDTO findByEmail(
            @Parameter(description = "Источник данных: 'memory' или 'db'", example = "db")
            @RequestParam(defaultValue = "memory") String source,
            @Parameter(description = "Email студента", example = "john@example.com")
            @PathVariable String email) {
        return chooseService(source).findByEmail(email);
    }

    @Operation(
            summary = "Обновление информации о студенте",
            description = "Обновляет данные студента по его email в выбранном источнике данных."
    )
    @PutMapping("/update")
    public StudentResponseDTO updateStudent(
            @Parameter(description = "Источник данных: 'memory' или 'db'", example = "db")
            @RequestParam(defaultValue = "memory") String source,
            @Valid @RequestBody StudentRequestDTO studentDTO) {
        return chooseService(source).updateStudent(studentDTO);
    }

    @Operation(
            summary = "Удаление студента по email",
            description = "Удаляет студента с указанным email из выбранного источника данных."
    )
    @DeleteMapping("/delete/{email}")
    public void deleteStudent(
            @Parameter(description = "Источник данных: 'memory' или 'db'", example = "db")
            @RequestParam(defaultValue = "memory") String source,
            @Parameter(description = "Email студента", example = "john@example.com")
            @PathVariable String email) {
        chooseService(source).deleteStudent(email);
    }

    @Operation(
            summary = "Поиск студента напрямую из базы данных по email",
            description = "Осуществляет прямой доступ к базе данных и возвращает студента по email, если он найден."
    )
    @GetMapping("/byEmail")
    public ResponseEntity<Student> getByEmail(
            @Parameter(description = "Email студента", example = "john@example.com")
            @RequestParam String email) {
        return studentRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Найти студентов старше указанного возраста",
            description = "Возвращает список студентов, возраст которых превышает заданное значение. Доступно только из базы данных."
    )
    @GetMapping("/ageGreaterThan/{age}")
    public List<StudentResponseDTO> getByAge(@PathVariable int age) {
        return studentRepository.findByAgeGreaterThan(age)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Operation(
            summary = "Поиск студентов по имени (JPQL)",
            description = "Выполняет запрос JPQL для получения студентов по имени (first name)."
    )
    @GetMapping("/firstName/{name}")
    public List<StudentResponseDTO> getByFirstName(@PathVariable String name) {
        return studentRepository.findByFirstNameJPQL(name)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Operation(
            summary = "Поиск студентов по фамилии (Native SQL)",
            description = "Выполняет нативный SQL-запрос для получения студентов по фамилии (last name)."
    )
    @GetMapping("/lastName/{name}")
    public List<StudentResponseDTO> getByLastName(@PathVariable String name) {
        return studentRepository.findByLastNameNative(name)
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }


    @Operation(
            summary = "Постраничный список студентов",
            description = "Позволяет получать студентов постранично с возможностью сортировки. Работает только с базой данных."
    )
    @GetMapping("/paged")
    public Page<StudentResponseDTO> getPagedStudents(
            @Parameter(description = "Номер страницы (начинается с 0)", example = "0")
            @RequestParam int page,
            @Parameter(description = "Размер страницы (количество студентов на странице)", example = "10")
            @RequestParam int size,
            @Parameter(description = "Поле для сортировки", example = "firstName")
            @RequestParam(defaultValue = "firstName") String sortBy,
            @Parameter(description = "Направление сортировки: 'asc' или 'desc'", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Источник данных, обязательно 'db'", example = "db")
            @RequestParam(required = false, defaultValue = "db") String source
    ) {
        if (!"db".equalsIgnoreCase(source)) {
            throw new UnsupportedOperationException("Pagination is only supported for 'db' source.");
        }

        return dbService.getPagedStudents(page, size, sortBy, direction);
    }

    @GetMapping("/by-book-title")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBookTitle(@RequestParam String title) {
        if (dbService instanceof DatabaseStudentServiceImpl dbImpl) {
            List<StudentResponseDTO> result = dbImpl.getStudentsByBookTitle(title)
                    .stream()
                    .map(studentMapper::toResponseDTOWithBooks)
                    .toList();
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/by-email-book")
    public ResponseEntity<StudentResponseDTO> getStudentWithBooksByEmail(@RequestParam String email) {
        if (dbService instanceof DatabaseStudentServiceImpl dbImpl) {
            return dbImpl.getStudentWithBooksByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/by-book-title-name")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBookTitleAndNameAndEmail(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String email) {
        if (dbService instanceof DatabaseStudentServiceImpl dbImpl) {
            List<StudentResponseDTO> result = dbImpl.searchByBookTitleAndNameAndEmail(title, name, email);
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(500).build();
    }
}
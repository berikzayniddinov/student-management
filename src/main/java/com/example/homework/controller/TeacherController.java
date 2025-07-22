package com.example.homework.controller;

import com.example.homework.model.Teacher;
import com.example.homework.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Get all teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping
    public List<Teacher> getAll() {
        return teacherService.getAll();
    }

    @Operation(summary = "Get teacher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher found"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable Long id) {
        return teacherService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher created successfully")
    })
    @PostMapping
    public Teacher create(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @Operation(summary = "Update existing teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher updated successfully"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher teacher) {
        try {
            return ResponseEntity.ok(teacherService.update(id, teacher));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete teacher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Teacher deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
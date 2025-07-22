package com.example.homework.service;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    List<StudentResponseDTO> findAllStudent(StudentRequestDTO requestDTO);

    StudentResponseDTO saveStudent(StudentRequestDTO requestDTO);
    StudentResponseDTO findByEmail(String email);
    StudentResponseDTO updateStudent(StudentRequestDTO requestDTO);

    void deleteStudent(String email);

    default Page<StudentResponseDTO> getPagedStudents(int page, int size, String sortBy, String direction) {
        throw new UnsupportedOperationException("Pagination not supported for this implementation.");
    }
}
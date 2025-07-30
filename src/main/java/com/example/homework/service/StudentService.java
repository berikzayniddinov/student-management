package com.example.homework.service;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentResponseDTO> findAllStudent(StudentRequestDTO requestDTO);

    StudentResponseDTO saveStudent(StudentRequestDTO requestDTO);
    StudentResponseDTO findByEmail(String email);
    StudentResponseDTO updateStudent(StudentRequestDTO requestDTO);

    void deleteStudent(String email);

    default Page<StudentResponseDTO> getPagedStudents(int page, int size, String sortBy, String direction) {
        throw new UnsupportedOperationException("Pagination not supported for this implementation.");
    }

    boolean emailExists(String email);
    Optional<StudentResponseDTO> getByEmail(String email);
    List<StudentResponseDTO> getByAgeGreaterThan(int age);
    List<StudentResponseDTO> getByFirstName(String name);
    List<StudentResponseDTO> getByLastName(String name);
    List<StudentResponseDTO> getByBookTitle(String title);

    // 🔽 Добавить эти 3 метода:
    List<StudentResponseDTO> getStudentsByBookTitle(String title);
    Optional<StudentResponseDTO> getStudentWithBooksByEmail(String email);
    List<StudentResponseDTO> searchByBookTitleAndNameAndEmail(String title, String name, String email);
}

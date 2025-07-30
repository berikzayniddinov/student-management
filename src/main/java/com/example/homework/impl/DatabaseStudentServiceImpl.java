package com.example.homework.impl;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.mapper.StudentMapper;
import com.example.homework.model.Student;
import com.example.homework.repository.DatabaseStudentDAO;
import com.example.homework.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("dbService")
@Qualifier("dbService")
@AllArgsConstructor
public class DatabaseStudentServiceImpl implements StudentService {
    private final DatabaseStudentDAO repository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponseDTO> findAllStudent(StudentRequestDTO requestDTO) {
        return repository.findAllStudent()
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO saveStudent(StudentRequestDTO requestDTO) {
        Student student = studentMapper.toEntity(requestDTO);
        Student saved = repository.saveStudent(student);
        return studentMapper.toDto(saved);
    }

    @Override
    public StudentResponseDTO findByEmail(String email) {
        Student student = repository.findByEmail(email);
        return studentMapper.toDto(student);
    }

    @Override
    public StudentResponseDTO updateStudent(StudentRequestDTO requestDTO) {
        Student updated = repository.updateStudent(studentMapper.toEntity(requestDTO));
        return studentMapper.toDto(updated);
    }

    @Override
    public void deleteStudent(String email) {
        repository.deleteStudent(email);
    }

    @Override
    public Page<StudentResponseDTO> getPagedStudents(int page, int size, String sortBy, String direction) {
        List<String> validSortFields = List.of("firstName", "lastName", "email");
        if (!validSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> students = repository.findAll(pageable);
        return students.map(studentMapper::toDto);
    }

    public List<StudentResponseDTO> getStudentsByBookTitle(String title) {
        return repository.findStudentsByBookTitle(title)
                .stream()
                .map(studentMapper::toResponseDTOWithBooks)
                .collect(Collectors.toList());
    }

    public Optional<StudentResponseDTO> getStudentWithBooksByEmail(String email) {
        return repository.findStudentWithBooksByEmail(email)
                .map(studentMapper::toResponseDTOWithBooks);
    }

    public List<StudentResponseDTO> searchByBookTitleAndNameAndEmail(String title, String name, String email) {
        return repository.searchByBookTitleAndNameAndEmail(title, name, email)
                .stream()
                .map(studentMapper::toResponseDTOWithBooks)
                .collect(Collectors.toList());
    }
    @Override
    public boolean emailExists(String email) {
        return repository.emailExists(email);
    }

    @Override
    public Optional<StudentResponseDTO> getByEmail(String email) {
        return repository.getByEmail(email)
                .map(studentMapper::toDto);
    }

    @Override
    public List<StudentResponseDTO> getByAgeGreaterThan(int age) {
        return repository.getByAgeGreaterThan(age)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDTO> getByFirstName(String name) {
        return repository.getByFirstName(name)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDTO> getByLastName(String name) {
        return repository.getByLastName(name)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDTO> getByBookTitle(String title) {
        return repository.getByBookTitle(title)
                .stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}
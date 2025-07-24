package com.example.homework.impl;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.mapper.StudentMapper;
import com.example.homework.model.Student;
import com.example.homework.repository.DatabaseStudentDAO;
import com.example.homework.repository.StudentJpaRepository;
import com.example.homework.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("dbService")
@Qualifier("dbService")
@AllArgsConstructor
public class DatabaseStudentServiceImpl implements StudentService {

    private final DatabaseStudentDAO repository;
    private final StudentMapper studentMapper;
    private final StudentJpaRepository studentJpaRepository;

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

    public List<Student> getStudentsByBookTitle(String title) {
        return studentJpaRepository.findStudentsByBookTitle(title);
    }

}
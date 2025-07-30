//package com.example.homework.impl;
//
//import com.example.homework.dto.StudentRequestDTO;
//import com.example.homework.dto.StudentResponseDTO;
//import com.example.homework.mapper.StudentMapper;
//import com.example.homework.model.Student;
//import com.example.homework.repository.InMemoryStudentDAO;
//import com.example.homework.service.StudentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service("inMemoryService")
//@Qualifier("inMemoryService")
//@RequiredArgsConstructor
//public abstract class IMemoryStudentServiceImpl implements StudentService {
//    @Qualifier("INMemoryServiceDAO")
//    private final InMemoryStudentDAO repository;
//    private final StudentMapper studentMapper;
//
//    @Override
//    public List<StudentResponseDTO> findAllStudent(StudentRequestDTO requestDTO) {
//        return repository.findAllStudent().stream()
//                .map(studentMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public StudentResponseDTO saveStudent(StudentRequestDTO requestDTO) {
//        Student student = studentMapper.toEntity(requestDTO);
//        Student saved = repository.saveStudent(student);
//        return studentMapper.toDto(saved);
//    }
//
//    @Override
//    public StudentResponseDTO findByEmail(String email) {
//        Student student = repository.findByEmail(email);
//        return studentMapper.toDto(student);
//    }
//
//    @Override
//    public StudentResponseDTO updateStudent(StudentRequestDTO requestDTO) {
//        Student student = studentMapper.toEntity(requestDTO);
//        Student updated = repository.updateStudent(student);
//        return studentMapper.toDto(updated);
//    }
//
//    @Override
//    public void deleteStudent(String email) {
//        repository.deleteStudent(email);
//    }
//}

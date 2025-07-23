package com.example.homework.mapper;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentRequestDTO dto);
    StudentResponseDTO toDto(Student student);
    StudentRequestDTO toRequestDto(Student student);
}

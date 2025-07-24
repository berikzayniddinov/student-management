package com.example.homework.mapper;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    })
    Student toEntity(StudentRequestDTO dto);

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    })
    StudentResponseDTO toDto(Student student);

    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    })
    StudentRequestDTO toRequestDto(Student student);
}

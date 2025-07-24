package com.example.homework.mapper;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.dto.StudentResponseDTO;
import com.example.homework.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = BookMapper.class)
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
           @Mapping(source = "id", target = "id"),
           @Mapping(source = "firstName", target = "firstName"),
           @Mapping(source = "lastName", target = "lastName"),
           @Mapping(source = "age", target = "age"),
           @Mapping(source = "email", target = "email"),
           @Mapping(source = "dateOfBirth", target = "dateOfBirth"),
           @Mapping(target = "books", ignore = true)
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

    default StudentResponseDTO toResponseDTOWithBooks(Student student) {
        StudentResponseDTO dto = toDto(student);
        if (student.getBooks() != null) {
            dto.setBooks(student.getBooks().stream()
                    .map(book -> Mappers.getMapper(BookMapper.class).toDto(book))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}

package com.example.homework.mapper;

import com.example.homework.elasticsearch.StudentDocument;
import com.example.homework.model.Book;
import com.example.homework.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentDocumentMapper {

    public StudentDocument toDocument(Student student) {
        return StudentDocument.builder()
                .id(student.getId().toString())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .age(student.getAge())
                .books(mapBooks(student.getBooks()))
                .build();
    }

    private List<StudentDocument.BookDocument> mapBooks(Set<Book> books) {
        if (books == null) return List.of();

        return books.stream()
                .map(book -> StudentDocument.BookDocument.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .year(book.getYear())
                        .isbn(book.getIsbn())
                        .build())
                .collect(Collectors.toList());
    }
}

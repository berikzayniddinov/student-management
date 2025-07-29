package com.example.homework.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDocument {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private List<BookDocument> books;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDocument {
        private String title;
        private String author;
        private int year;
        private String isbn;
    }
}

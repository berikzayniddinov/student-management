package com.example.homework.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "book", schema = "adamant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int year;
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private Set<Student> students;
}


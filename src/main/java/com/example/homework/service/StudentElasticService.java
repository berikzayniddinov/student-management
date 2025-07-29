package com.example.homework.service;


import com.example.homework.model.Student;
import com.example.homework.elasticsearch.StudentDocument;

import java.util.List;

public interface StudentElasticService {
    void save(Student student);
    void deleteById(Long id);
    List<StudentDocument> findByEmail(String email);
}

package com.example.homework.service;


import com.example.homework.model.Student;
import com.example.homework.elasticsearch.StudentDocument;
import org.springframework.context.annotation.Profile;

import java.util.List;
@Profile("with-elastic")
public interface StudentElasticService {
    void save(Student student);
    void deleteById(Long id);
    List<StudentDocument> findByEmail(String email);
}

package com.example.homework.impl;

import com.example.homework.elasticsearch.StudentDocument;
import com.example.homework.model.Student;
import com.example.homework.repository.StudentElasticRepository;
import com.example.homework.service.StudentElasticService;
import com.example.homework.mapper.StudentDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentElasticService")
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentElasticService {

    private final StudentElasticRepository repository;
    private final StudentDocumentMapper mapper;

    @Override
    public void save(Student student) {
        StudentDocument document = mapper.toDocument(student);
        repository.save(document);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(String.valueOf(id));
    }

    @Override
    public List<StudentDocument> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

package com.example.homework.runner;

import com.example.homework.model.Student;
import com.example.homework.repository.StudentJpaRepository;
import com.example.homework.service.StudentElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticsearchSyncRunner implements ApplicationRunner {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentElasticService studentElasticService;

    @Override
    public void run(ApplicationArguments args) {
        List<Student> allStudents = studentJpaRepository.findAllWithBooks();
        allStudents.forEach(studentElasticService::save);
        System.out.println("✅ Synced " + allStudents.size() + " students to Elasticsearch.");
    }
}


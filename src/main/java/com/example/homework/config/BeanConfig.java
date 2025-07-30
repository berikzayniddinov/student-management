package com.example.homework.config;

import com.example.homework.repository.DatabaseStudentDAO;
import com.example.homework.repository.InMemoryStudentDAO;
import com.example.homework.repository.StudentJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

//    @Bean("inMemoryRepo")
//    public InMemoryStudentDAO inMemoryStudentDAO() {
//        return new InMemoryStudentDAO();
//    }

//    @Bean("databaseRepo")
//    public DatabaseStudentDAO databaseStudentDAO(StudentJpaRepository studentJpaRepository) {
//        return new DatabaseStudentDAO(studentJpaRepository);
//    }
}
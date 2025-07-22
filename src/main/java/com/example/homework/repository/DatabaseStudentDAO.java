package com.example.homework.repository;

import com.example.homework.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public class DatabaseStudentDAO {

    private final StudentJpaRepository studentJpaRepository;

    @Autowired
    public DatabaseStudentDAO(StudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }

    public List<Student> findAllStudent() {
        return studentJpaRepository.findAll();
    }

    public Student saveStudent(Student student) {
        return studentJpaRepository.save(student);
    }

    public Student findByEmail(String email) {
        return studentJpaRepository.findByEmail(email).orElse(null);
    }

    public Student updateStudent(Student student) {
        return studentJpaRepository.save(student); // JPA save = update если ID существует
    }

    public void deleteStudent(String email) {
        Student existing = studentJpaRepository.findByEmail(email).orElse(null);
        if (existing != null) {
            studentJpaRepository.delete(existing);
        }
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentJpaRepository.findAll(pageable);
    }
}

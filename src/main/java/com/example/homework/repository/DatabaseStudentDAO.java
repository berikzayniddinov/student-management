package com.example.homework.repository;

import com.example.homework.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
        return studentJpaRepository.save(student);
    }

    public void deleteStudent(String email) {
        studentJpaRepository.findByEmail(email)
                .ifPresent(studentJpaRepository::delete);
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentJpaRepository.findAll(pageable);
    }

    public List<Student> findStudentsByBookTitle(String title) {
        return studentJpaRepository.findStudentsByBookTitle(title);
    }

    public Optional<Student> findStudentWithBooksByEmail(String email) {
        return studentJpaRepository.findStudentWithBooksByEmail(email);
    }

    public List<Student> searchByBookTitleAndNameAndEmail(String title, String name, String email) {
        return studentJpaRepository.searchByBookTitleAndNameAndEmail(title, name, email);
    }
    public boolean emailExists(String email) {
        return studentJpaRepository.findByEmail(email).isPresent();
    }

    public Optional<Student> getByEmail(String email) {
        return studentJpaRepository.findByEmail(email);
    }

    public List<Student> getByAgeGreaterThan(int age) {
        return studentJpaRepository.findByAgeGreaterThan(age);
    }

    public List<Student> getByFirstName(String name) {
        return studentJpaRepository.findByFirstName(name);
    }

    public List<Student> getByLastName(String name) {
        return studentJpaRepository.findByLastName(name);
    }

    public List<Student> getByBookTitle(String title) {
        return studentJpaRepository.findStudentsByBookTitle(title);
    }
}

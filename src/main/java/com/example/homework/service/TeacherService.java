package com.example.homework.service;

import com.example.homework.model.Teacher;
import com.example.homework.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getById(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(Long id, Teacher teacher) {
        return teacherRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(teacher.getFirstName());
                    existing.setLastName(teacher.getLastName());
                    existing.setSubject(teacher.getSubject());
                    existing.setEmail(teacher.getEmail());
                    return teacherRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}

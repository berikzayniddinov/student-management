package com.example.homework.repository;

import com.example.homework.model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;


public class InMemoryStudentDAO {
    private final List<Student> STUDENTS = new ArrayList<>();

    public List<Student> findAllStudent() {
        return STUDENTS;
    }

    public Student saveStudent(Student student) {
        STUDENTS.add(student);
        return student;
    }

    public Student findByEmail(String email) {
        return STUDENTS.stream()
                .filter(element -> element.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Student updateStudent(Student student) {
        var studentIndex = IntStream.range(0, STUDENTS.size())
                .filter(index -> STUDENTS.get(index).getEmail().equals(student.getEmail()))
                .findFirst()
                .orElse(-1);
        if (studentIndex > -1) {
            STUDENTS.set(studentIndex, student);
            return student;
        }
        return null;
    }

    public void deleteStudent(String email) {
        var student = findByEmail(email);
        if (student != null) {
            STUDENTS.remove(student);
        }
    }

    public List<String> getAllEmailsSortedByAge() {
        return STUDENTS.stream()
                .sorted(Comparator.comparingInt(Student::getAge))
                .map(Student::getEmail)
                .toList();
    }
}

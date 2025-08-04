package com.example.homework.aspects;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.mapper.StudentMapper;
import com.example.homework.model.Student;
import com.example.homework.rabbitmq.producer.StudentMessageProducer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class StudentAspect {

    private final StudentMessageProducer studentMessageProducer;
    private final StudentMapper studentMapper;

    @AfterReturning(
            pointcut = "execution(* com.example.homework.service.StudentService.saveStudent(..)) && args(dto)",
            returning = "result",
            argNames = "dto,result")
    public void afterStudentSaved(StudentRequestDTO dto, Object result) {
        Student student = studentMapper.toEntity(dto);
        studentMessageProducer.sendStudent(student);
    }

    @AfterReturning(
            pointcut = "execution(* com.example.homework.service.StudentService.updateStudent(..)) && args(dto)",
            returning = "result",
            argNames = "dto,result")
    public void afterStudentUpdated(StudentRequestDTO dto, Object result) {
        Student student = studentMapper.toEntity(dto);
        studentMessageProducer.sendStudent(student);
    }
}

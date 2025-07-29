package com.example.homework.aspects;

import com.example.homework.dto.StudentRequestDTO;
import com.example.homework.mapper.StudentMapper;
import com.example.homework.model.Student;
import com.example.homework.repository.StudentJpaRepository;
import com.example.homework.service.StudentElasticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class StudentElasticAspect {

    private final StudentElasticService elasticService;
    private final StudentMapper studentMapper;
    private final StudentJpaRepository studentJpaRepository;

    @AfterReturning(
            pointcut = "execution(* com.example.homework.service.StudentService.saveStudent(..)) && args(dto)",
            returning = "result",
            argNames = "dto,result")
    public void afterSave(StudentRequestDTO dto, Object result) {
        Student student = studentMapper.toEntity(dto);
        elasticService.save(student);
        log.info("Saved student to Elasticsearch: {}", student.getEmail());
    }

    @AfterReturning(
            pointcut = "execution(* com.example.homework.service.StudentService.updateStudent(..)) && args(dto)",
            returning = "result",
            argNames = "dto,result")
    public void afterUpdate(StudentRequestDTO dto, Object result) {
        Student student = studentMapper.toEntity(dto);
        elasticService.save(student);
        log.info("Updated student in Elasticsearch: {}", student.getEmail());
    }

    @After(
            value = "execution(* com.example.homework.service.StudentService.deleteStudent(..)) && args(email)"
    )
    public void afterDelete(String email) {
        studentJpaRepository.findByEmail(email).ifPresent(student -> {
            elasticService.deleteById(student.getId());
            log.info("Deleted student from Elasticsearch: {}", email);
        });
    }
}

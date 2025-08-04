package com.example.homework.rabbitmq.consumer;

import com.example.homework.model.Student;
import com.example.homework.service.StudentElasticService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Slf4j
@Component
public class StudentMessageConsumer {
    private final StudentElasticService studentElasticService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "studentQueue")
    public void receiveStudent(String messageJson) {
        if (messageJson == null || messageJson.trim().isEmpty()) {
            log.warn("Received empty message from RabbitMQ.");
            return;
        }
        try {
            Student student = objectMapper.readValue(messageJson, Student.class);
            log.info("Received student from RabbitMQ: {}", student);
            studentElasticService.save(student);
        } catch (Exception e) {
            log.error("Failed to deserialize student JSON: {}", messageJson, e);
        }
    }

}
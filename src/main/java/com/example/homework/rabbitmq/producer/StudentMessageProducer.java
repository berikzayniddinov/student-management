package com.example.homework.rabbitmq.producer;

import com.example.homework.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import static com.example.homework.config.RabbitMQConfig.EXCHANGE;
import static com.example.homework.config.RabbitMQConfig.ROUTING_KEY;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendStudent(Student student) {
        log.info("Sending student to RabbitMQ: {}", student.getEmail());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, student);
    }
}

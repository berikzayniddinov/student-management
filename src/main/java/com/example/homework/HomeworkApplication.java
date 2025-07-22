package com.example.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.homework.repository")
public class HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

}
//Injection point
// Spring DATA
// Hibernate
// JPA
// заимплементить 3 метода куери в спринг дате
// 1 метод куери 2 jpql куери 3 нативный куери
// что такое runtime vs compile time
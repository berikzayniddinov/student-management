package com.example.homework.controller;

import com.example.homework.elasticsearch.StudentDocument;
import com.example.homework.service.StudentElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Profile("with-elastic")
@RestController
@RequestMapping("/api/elastic/students")
@RequiredArgsConstructor
public class StudentElasticController {

    private final StudentElasticService elasticService;

    @GetMapping("/email")
    public List<StudentDocument> findByEmail(@RequestParam String email) {
        return elasticService.findByEmail(email);
    }
}

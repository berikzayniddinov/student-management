package com.example.homework.repository;

import com.example.homework.elasticsearch.StudentDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
@Profile("with-elastic")
public interface StudentElasticRepository extends ElasticsearchRepository<StudentDocument, String> {
    List<StudentDocument> findByEmail(String email);
}

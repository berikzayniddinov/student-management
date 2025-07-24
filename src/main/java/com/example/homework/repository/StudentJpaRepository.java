package com.example.homework.repository;

import com.example.homework.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    List<Student> findByAgeGreaterThan(int age);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName")
    List<Student> findByFirstNameJPQL(@Param("firstName") String firstName);

    @Query(value = "SELECT * FROM adamant.students WHERE last_name = :lastName", nativeQuery = true)
    List<Student> findByLastNameNative(@Param("lastName") String lastName);

    Page<Student> findAll(Pageable pageable);

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN s.books b WHERE b.title = :title")
    List<Student> findStudentsByBookTitle(@Param("title") String title);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.books WHERE s.email = :email")
    Optional<Student> findStudentWithBooksByEmail(@Param("email") String email);

    @Query("""
    SELECT DISTINCT s FROM Student s
    LEFT JOIN FETCH s.books b
    WHERE LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%'))
      AND LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
      AND LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))
""")
    List<Student> searchByBookTitleAndNameAndEmail(@Param("title") String title,
                                                   @Param("name") String name,
                                                   @Param("email") String email);
}

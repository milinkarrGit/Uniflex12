package com.uniflex.app.repository;

import com.uniflex.app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByCode(String code);
    Optional<Student> findByUser_Id(Long userId);
}
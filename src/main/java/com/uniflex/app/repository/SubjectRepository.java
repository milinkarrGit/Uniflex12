package com.uniflex.app.repository;

import com.uniflex.app.entity.Subject;
import com.uniflex.app.entity.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByClassGroup(ClassGroup classGroup);
    List<Subject> findByActiveTrue();
}
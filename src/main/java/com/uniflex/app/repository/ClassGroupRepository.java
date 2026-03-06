package com.uniflex.app.repository;

import com.uniflex.app.entity.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
    List<ClassGroup> findByActiveTrue();
}
package com.spring.registration.model.repo;

import com.spring.registration.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Long> {
    List<Course> findByCategoryId(Long id);

    List<Course> findByCategoryName(String name);
    List<Course> findByStartBefore(LocalDate date);
}

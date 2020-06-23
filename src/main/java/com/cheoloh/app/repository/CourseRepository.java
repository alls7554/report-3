package com.cheoloh.app.repository;

import com.cheoloh.app.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

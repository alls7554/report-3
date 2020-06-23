package com.cheoloh.app.repository;

import com.cheoloh.app.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByName(String name);
    List<Student> findAllByEmailIsLike(String email);

}

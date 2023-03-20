package com.example.reactivedemo.repository;

import com.example.reactivedemo.model.Teacher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TeacherRepository extends ReactiveCrudRepository<Teacher, Long> {
}

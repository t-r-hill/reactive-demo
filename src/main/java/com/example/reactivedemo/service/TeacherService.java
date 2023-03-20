package com.example.reactivedemo.service;

import com.example.reactivedemo.model.Teacher;
import com.example.reactivedemo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TransactionalOperator transactionalOperator;

    public Mono<Teacher> getTeacherById(Long id){
        return transactionalOperator.transactional(teacherRepository.findById(id));
    }

    public Flux<Teacher> getALlTeachers(){
        return transactionalOperator.transactional(teacherRepository.findAll());
    }

    public Mono<Teacher> saveTeacher(Teacher teacher){
        return transactionalOperator.transactional(teacherRepository.save(teacher));
    }

    public Flux<Teacher> saveAllTeachers(Flux<Teacher> teachers){
        return transactionalOperator.transactional(teacherRepository.saveAll(teachers));
    }

    public Mono<Teacher> updateTeacher(Long id, Mono<Teacher> teacher) {
        return teacher.map(t -> {
            t.setId(id);
            return t;
        }).flatMap(teacher1 -> teacherRepository.save(teacher1));
    }

    public Mono<Void> deleteTeacher(Long id){
        return  teacherRepository.findById(id).map(t -> teacherRepository.delete(t)).then();
    }
}

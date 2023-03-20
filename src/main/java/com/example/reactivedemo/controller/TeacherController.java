package com.example.reactivedemo.controller;

import com.example.reactivedemo.model.Teacher;
import com.example.reactivedemo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    public Mono<ServerResponse> getAllTeachers(ServerRequest request) {
        return ServerResponse.ok().body(teacherService.getALlTeachers(), Teacher.class);
    }

    public Mono<ServerResponse> getTeacherById(ServerRequest request) {
        return ServerResponse.ok().body(teacherService.getTeacherById(Long.valueOf(request.pathVariable("id"))), Teacher.class);
    }

    public Mono<ServerResponse> addNewTeacher(ServerRequest request) {
        return request.bodyToMono(Teacher.class)
                .flatMap(t -> teacherService.saveTeacher(t))
                .flatMap(t -> ServerResponse.accepted().body(t, Teacher.class));
    }

    public Mono<ServerResponse> updateTeacher(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<Teacher> tMono = request.bodyToMono(Teacher.class);
        return ServerResponse.accepted().body(teacherService.updateTeacher(id, tMono), Teacher.class);
    }

    public Mono<ServerResponse> deleteTeacher(ServerRequest request) {
        return teacherService.deleteTeacher(Long.valueOf(request.pathVariable("id")))
                .then(ServerResponse.ok().build());
    }
}

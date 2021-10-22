package com.goals.course.journal.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Service
public class ManagerURL {

    @Value("${app.microservice.manager.url}")
    private String baseUrl;

    public String studentById(final UUID studentId) {
        return baseUrl + "/api/manager/students/%s".formatted(studentId);
    }

    public String courseById(final UUID courseId) {
        return baseUrl + "/api/manager/courses/%s".formatted(courseId);
    }

    public String lessonsById(final UUID lessonId) {
        return baseUrl + "/api/manager/lessons/%s".formatted(lessonId);
    }
}

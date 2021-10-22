package com.goals.course.journal.service;

import com.goals.course.journal.dto.CourseDTO;

import java.util.Optional;
import java.util.UUID;

public interface CourseProvider {
    Optional<CourseDTO> findCourseById(final UUID courseId);
}

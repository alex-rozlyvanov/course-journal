package com.goals.course.journal.service;

import com.goals.course.journal.dto.LessonDTO;

import java.util.Optional;
import java.util.UUID;

public interface LessonProvider {
    Optional<LessonDTO> findLessonById(final UUID lessonId);
}

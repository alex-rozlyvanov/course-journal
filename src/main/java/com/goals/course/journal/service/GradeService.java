package com.goals.course.journal.service;

import com.goals.course.journal.dto.GradeDTO;

import java.util.UUID;

public interface GradeService {
    GradeDTO gradeForLesson(final GradeDTO gradeDTO);

    GradeDTO getGradeByLessonIdAndStudentId(final UUID lessonId, final UUID studentId);
}

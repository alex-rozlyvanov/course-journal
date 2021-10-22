package com.goals.course.journal.controller;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.dto.GradeFilters;

public interface GradeController {
    GradeDTO gradeForLesson(final GradeDTO gradeDTO);

    GradeDTO getGradeByLessonIdAndStudentId(final GradeFilters gradeFilters);
}

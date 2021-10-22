package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.GradeEntity;
import com.goals.course.journal.dto.GradeDTO;

public interface GradeMapper {
    GradeEntity mapToGrade(final GradeDTO gradeDTO);

    GradeDTO mapToGradeDTO(final GradeEntity gradeEntity);
}

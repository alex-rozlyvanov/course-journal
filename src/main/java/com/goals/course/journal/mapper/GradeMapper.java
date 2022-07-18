package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.GradeEntity;
import com.goals.course.journal.dto.GradeDTO;
import org.springframework.stereotype.Component;

@Component
public class GradeMapper {

    public GradeEntity mapToGrade(final GradeDTO gradeDTO) {
        return new GradeEntity()
                .setId(gradeDTO.getId())
                .setStudentId(gradeDTO.getStudentId())
                .setLessonId(gradeDTO.getLessonId())
                .setGrade(gradeDTO.getGrade());
    }

    public GradeDTO mapToGradeDTO(final GradeEntity gradeEntity) {
        return GradeDTO.builder()
                .id(gradeEntity.getId())
                .studentId(gradeEntity.getStudentId())
                .lessonId(gradeEntity.getLessonId())
                .grade(gradeEntity.getGrade())
                .build();
    }
}

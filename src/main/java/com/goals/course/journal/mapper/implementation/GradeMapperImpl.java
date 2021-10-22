package com.goals.course.journal.mapper.implementation;

import com.goals.course.journal.dao.entity.GradeEntity;
import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.mapper.GradeMapper;
import org.springframework.stereotype.Service;

@Service
public class GradeMapperImpl implements GradeMapper {
    @Override
    public GradeEntity mapToGrade(final GradeDTO gradeDTO) {
        return new GradeEntity()
                .setId(gradeDTO.getId())
                .setStudentId(gradeDTO.getStudentId())
                .setLessonId(gradeDTO.getLessonId())
                .setGrade(gradeDTO.getGrade());
    }

    @Override
    public GradeDTO mapToGradeDTO(final GradeEntity gradeEntity) {
        return GradeDTO.builder()
                .id(gradeEntity.getId())
                .studentId(gradeEntity.getStudentId())
                .lessonId(gradeEntity.getLessonId())
                .grade(gradeEntity.getGrade())
                .build();
    }
}

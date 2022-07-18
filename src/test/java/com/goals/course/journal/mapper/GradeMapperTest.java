package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.GradeEntity;
import com.goals.course.journal.dto.GradeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GradeMapperTest {

    @InjectMocks
    private GradeMapper service;

    @Test
    void mapToGrade_id_checkResult() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();

        // WHEN
        final var result = service.mapToGrade(gradeDTO);

        // THEN
        assertThat(result.getId()).isSameAs(gradeId);
    }

    @Test
    void mapToGrade_studentId_checkResult() {
        // GIVEN
        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final var gradeDTO = GradeDTO.builder().studentId(studentId).build();

        // WHEN
        final var result = service.mapToGrade(gradeDTO);

        // THEN
        assertThat(result.getStudentId()).isSameAs(studentId);
    }

    @Test
    void mapToGrade_lessonId_checkResult() {
        // GIVEN
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final var gradeDTO = GradeDTO.builder().lessonId(lessonId).build();

        // WHEN
        final var result = service.mapToGrade(gradeDTO);

        // THEN
        assertThat(result.getLessonId()).isSameAs(lessonId);
    }

    @Test
    void mapToGrade_grade_checkResult() {
        // GIVEN
        final var gradeDTO = GradeDTO.builder().grade(99).build();

        // WHEN
        final var result = service.mapToGrade(gradeDTO);

        // THEN
        assertThat(result.getGrade()).isEqualTo(99);
    }

    @Test
    void mapToGradeDTO_id_checkResult() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = new GradeEntity().setId(gradeId);

        // WHEN
        final var result = service.mapToGradeDTO(gradeDTO);

        // THEN
        assertThat(result.getId()).isSameAs(gradeId);
    }

    @Test
    void mapToGradeDTO_studentId_checkResult() {
        // GIVEN
        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final var gradeDTO = new GradeEntity().setStudentId(studentId);

        // WHEN
        final var result = service.mapToGradeDTO(gradeDTO);

        // THEN
        assertThat(result.getStudentId()).isSameAs(studentId);
    }

    @Test
    void mapToGradeDTO_lessonId_checkResult() {
        // GIVEN
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final var gradeDTO = new GradeEntity().setLessonId(lessonId);

        // WHEN
        final var result = service.mapToGradeDTO(gradeDTO);

        // THEN
        assertThat(result.getLessonId()).isSameAs(lessonId);
    }

    @Test
    void mapToGradeDTO_grade_checkResult() {
        // GIVEN
        final var gradeDTO = new GradeEntity().setGrade(98);

        // WHEN
        final var result = service.mapToGradeDTO(gradeDTO);

        // THEN
        assertThat(result.getGrade()).isEqualTo(98);
    }
}

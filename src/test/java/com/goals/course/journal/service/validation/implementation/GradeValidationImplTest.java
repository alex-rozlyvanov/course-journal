package com.goals.course.journal.service.validation.implementation;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.dto.LessonDTO;
import com.goals.course.journal.dto.UserDTO;
import com.goals.course.journal.service.LessonProvider;
import com.goals.course.journal.service.StudentProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeValidationImplTest {

    @Mock
    private StudentProvider mockStudentProvider;
    @Mock
    private LessonProvider mockLessonProvider;

    @InjectMocks
    private GradeValidationImpl service;

    @Test
    void validateGrade_callFindStudentById() {
        // GIVEN
        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().studentId(studentId).grade(50).build();

        // WHEN
        service.validateGrade(gradeDTO);

        // THEN
        verify(mockStudentProvider).findStudentById(studentId);
    }

    @Test
    void validateGrade_checkResult() {
        // GIVEN
        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().studentId(studentId).grade(50).build();
        when(mockStudentProvider.findStudentById(any())).thenReturn(Optional.of(UserDTO.builder().build()));
        when(mockLessonProvider.findLessonById(any())).thenReturn(Optional.of(LessonDTO.builder().build()));

        // WHEN
        final var result = service.validateGrade(gradeDTO);

        // THEN
        assertTrue(result.isValid());
    }

    @Test
    void validateGrade_studentNotFound_checkResult() {
        // GIVEN
        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().studentId(studentId).grade(50).build();

        // WHEN
        final var result = service.validateGrade(gradeDTO);

        // THEN
        assertFalse(result.isValid());
        assertThat(result.getMessage()).isEqualTo("Student '00000000-0000-0000-0000-000000000001' not found!");
    }

    @Test
    void validateGrade_callFindLessonById() {
        // GIVEN
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final var gradeDTO = GradeDTO.builder().lessonId(lessonId).grade(50).build();
        when(mockStudentProvider.findStudentById(any())).thenReturn(Optional.of(UserDTO.builder().build()));

        // WHEN
        service.validateGrade(gradeDTO);

        // THEN
        verify(mockLessonProvider).findLessonById(lessonId);
    }

    @Test
    void validateGrade_lessonNotFound_checkResult() {
        // GIVEN
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final var gradeDTO = GradeDTO.builder().lessonId(lessonId).grade(50).build();
        when(mockStudentProvider.findStudentById(any())).thenReturn(Optional.of(UserDTO.builder().build()));
        when(mockLessonProvider.findLessonById(any())).thenReturn(Optional.empty());

        // WHEN
        final var result = service.validateGrade(gradeDTO);

        // THEN
        assertFalse(result.isValid());
        assertThat(result.getMessage()).isEqualTo("Lesson '00000000-0000-0000-0000-000000000002' not found!");
    }

    @ParameterizedTest
    @MethodSource("gradesSource")
    void validateGrade_grade_checkResult(final Integer grade, final ValidationResult validationResult) {
        // GIVEN
        final var gradeDTO = GradeDTO.builder().grade(grade).build();
        lenient().when(mockStudentProvider.findStudentById(any())).thenReturn(Optional.of(UserDTO.builder().build()));
        lenient().when(mockLessonProvider.findLessonById(any())).thenReturn(Optional.of(LessonDTO.builder().build()));

        // WHEN
        final var result = service.validateGrade(gradeDTO);

        // THEN
        assertThat(result.isValid()).isEqualTo(validationResult.isValid());
        assertThat(result.getMessage()).isEqualTo(validationResult.getMessage());
    }

    private static Stream<Arguments> gradesSource() {
        return Stream.of(
                Arguments.of(null, ValidationResult.invalid("Grade should be in closed range [0,100]")),
                Arguments.of(-1, ValidationResult.invalid("Grade should be in closed range [0,100]")),
                Arguments.of(101, ValidationResult.invalid("Grade should be in closed range [0,100]")),
                Arguments.of(0, ValidationResult.valid()),
                Arguments.of(100, ValidationResult.valid())
        );
    }
}

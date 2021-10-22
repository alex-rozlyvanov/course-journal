package com.goals.course.journal.service.validation.implementation;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.service.LessonProvider;
import com.goals.course.journal.service.StudentProvider;
import com.goals.course.journal.service.validation.GradeValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class GradeValidationImpl implements GradeValidation {
    private final StudentProvider studentProvider;
    private final LessonProvider lessonProvider;

    @Override
    public ValidationResult validateGrade(final GradeDTO gradeDTO) {
        log.info("Validating grade {}", gradeDTO);
        if (gradeIsNotValid(gradeDTO)) {
            return ValidationResult.invalid("Grade should be in closed range [0,100]");
        }

        if (studentIsNotValid(gradeDTO)) {
            return ValidationResult.invalid("Student '%s' not found!".formatted(gradeDTO.getStudentId()));
        }

        if (lessonsNotValid(gradeDTO)) {
            return ValidationResult.invalid("Lesson '%s' not found!".formatted(gradeDTO.getLessonId()));
        }

        return ValidationResult.valid();
    }

    private boolean gradeIsNotValid(final GradeDTO gradeDTO) {
        return isNull(gradeDTO.getGrade()) || gradeDTO.getGrade() < 0 || gradeDTO.getGrade() > 100;
    }

    private boolean studentIsNotValid(final GradeDTO gradeDTO) {
        final var studentId = gradeDTO.getStudentId();
        final var studentById = studentProvider.findStudentById(studentId);

        return studentById.isEmpty();
    }

    private boolean lessonsNotValid(final GradeDTO gradeDTO) {
        final var lessonId = gradeDTO.getLessonId();
        final var lessonById = lessonProvider.findLessonById(lessonId);

        return lessonById.isEmpty();
    }
}

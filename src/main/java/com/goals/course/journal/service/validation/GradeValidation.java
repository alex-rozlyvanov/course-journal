package com.goals.course.journal.service.validation;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.service.LessonProvider;
import com.goals.course.journal.service.StudentProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class GradeValidation {
    private final StudentProvider studentProvider;
    private final LessonProvider lessonProvider;

    public Mono<ValidationResult> validateGrade(final GradeDTO gradeDTO) {
        log.info("Validating grade {}", gradeDTO);

        return gradeIsNotValid(gradeDTO)
                .flatMap(validationResult ->
                        ValidationResult.ifValidCheckNext(validationResult, () -> studentExists(gradeDTO))
                )
                .flatMap(validationResult ->
                        ValidationResult.ifValidCheckNext(validationResult, () -> lessonsExists(gradeDTO))
                );
    }

    private Mono<ValidationResult> gradeIsNotValid(final GradeDTO gradeDTO) {
        final var gradeIsNotValid = isNull(gradeDTO.getGrade()) || gradeDTO.getGrade() < 0 || gradeDTO.getGrade() > 100;

        return gradeIsNotValid
                ? Mono.just(ValidationResult.invalid("Grade should be in closed range [0,100]"))
                : ValidationResult.validMono();
    }

    private Mono<ValidationResult> studentExists(final GradeDTO gradeDTO) {
        final var studentId = gradeDTO.getStudentId();

        return studentProvider.findStudentById(studentId)
                .flatMap(s -> ValidationResult.validMono())
                .switchIfEmpty(Mono.fromSupplier(
                        () -> ValidationResult.invalid("Student '%s' not found!".formatted(gradeDTO.getStudentId()))
                ));
    }

    private Mono<ValidationResult> lessonsExists(final GradeDTO gradeDTO) {
        final var lessonId = gradeDTO.getLessonId();

        return lessonProvider.findLessonById(lessonId)
                .flatMap(s -> ValidationResult.validMono())
                .switchIfEmpty(Mono.fromSupplier(
                        () -> ValidationResult.invalid("Lesson '%s' not found!".formatted(gradeDTO.getLessonId()))
                ));
    }
}

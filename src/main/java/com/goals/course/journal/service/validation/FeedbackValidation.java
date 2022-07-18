package com.goals.course.journal.service.validation;

import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.service.CourseProvider;
import com.goals.course.journal.service.StudentProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class FeedbackValidation {
    private final StudentProvider studentProvider;
    private final CourseProvider courseProvider;

    public Mono<ValidationResult> validateFeedback(final FeedbackDTO feedbackDTO) {
        log.info("Validating feedback {}", feedbackDTO);

        return studentIsNotValid(feedbackDTO)
                .flatMap(validationResult ->
                        ValidationResult.ifValidCheckNext(validationResult, () -> courseNotValid(feedbackDTO))
                )
                .switchIfEmpty(ValidationResult.validMono());
    }

    private Mono<ValidationResult> studentIsNotValid(final FeedbackDTO feedbackDTO) {
        final var studentId = feedbackDTO.getStudentId();
        return studentProvider.findStudentById(studentId)
                .flatMap(s -> ValidationResult.validMono())
                .switchIfEmpty(Mono.fromSupplier(
                        () -> ValidationResult.invalid("Student '%s' not found!".formatted(feedbackDTO.getStudentId()))
                ));
    }

    private Mono<ValidationResult> courseNotValid(final FeedbackDTO feedbackDTO) {
        final var courseId = feedbackDTO.getCourseId();
        return courseProvider.findCourseById(courseId)
                .flatMap(s -> ValidationResult.validMono())
                .switchIfEmpty(Mono.fromSupplier(
                        () -> ValidationResult.invalid("Course '%s' not found!".formatted(feedbackDTO.getCourseId()))
                ));
    }
}

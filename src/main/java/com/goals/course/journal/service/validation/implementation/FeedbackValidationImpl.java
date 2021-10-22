package com.goals.course.journal.service.validation.implementation;

import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.service.CourseProvider;
import com.goals.course.journal.service.StudentProvider;
import com.goals.course.journal.service.validation.FeedbackValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FeedbackValidationImpl implements FeedbackValidation {
    private final StudentProvider studentProvider;
    private final CourseProvider courseProvider;

    @Override
    public ValidationResult validateFeedback(final FeedbackDTO feedbackDTO) {
        log.info("Validating feedback {}", feedbackDTO);

        if (studentIsNotValid(feedbackDTO)) {
            return ValidationResult.invalid("Student '%s' not found!".formatted(feedbackDTO.getStudentId()));
        }

        if (courseNotValid(feedbackDTO)) {
            return ValidationResult.invalid("Course '%s' not found!".formatted(feedbackDTO.getCourseId()));
        }

        return ValidationResult.valid();
    }

    private boolean studentIsNotValid(final FeedbackDTO feedbackDTO) {
        final var studentId = feedbackDTO.getStudentId();
        final var studentById = studentProvider.findStudentById(studentId);

        return studentById.isEmpty();
    }

    private boolean courseNotValid(final FeedbackDTO feedbackDTO) {
        final var courseId = feedbackDTO.getCourseId();
        final var courseById = courseProvider.findCourseById(courseId);

        return courseById.isEmpty();
    }
}

package com.goals.course.journal.service.validation;

import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.service.validation.implementation.ValidationResult;

public interface FeedbackValidation {
    ValidationResult validateFeedback(final FeedbackDTO feedbackDTO);
}

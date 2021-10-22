package com.goals.course.journal.service.validation;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.service.validation.implementation.ValidationResult;

public interface GradeValidation {
    ValidationResult validateGrade(final GradeDTO gradeDTO);
}

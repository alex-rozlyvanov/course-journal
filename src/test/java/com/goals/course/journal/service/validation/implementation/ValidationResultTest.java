package com.goals.course.journal.service.validation.implementation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationResultTest {

    @Test
    void valid_checkResult() {
        // GIVEN

        // WHEN
        final var result = ValidationResult.valid();

        // THEN
        assertThat(result.isValid()).isTrue();
        assertThat(result.isNotValid()).isFalse();
        assertThat(result.getMessage()).isNull();
    }

    @Test
    void invalid_checkResult() {
        // GIVEN

        // WHEN
        final var result = ValidationResult.invalid("test message");

        // THEN
        assertThat(result.isValid()).isFalse();
        assertThat(result.isNotValid()).isTrue();
        assertThat(result.getMessage()).isEqualTo("test message");
    }

}


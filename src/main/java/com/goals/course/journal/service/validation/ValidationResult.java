package com.goals.course.journal.service.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class ValidationResult {

    private static final ValidationResult VALIDATION_RESULT = new ValidationResult(true, null);
    private static final Mono<ValidationResult> VALIDATION_RESULT_MONO = Mono.just(VALIDATION_RESULT);

    private final boolean valid;
    private final String message;

    public static Mono<ValidationResult> ifValidCheckNext(final ValidationResult validationResult,
                                                          final Supplier<Mono<ValidationResult>> nextValidationSupplier) {
        if (validationResult.isValid()) {
            return nextValidationSupplier.get();
        }
        return Mono.just(validationResult);
    }

    public static ValidationResult valid() {
        return VALIDATION_RESULT;
    }

    public static Mono<ValidationResult> validMono() {
        return VALIDATION_RESULT_MONO;
    }

    public static ValidationResult invalid(final String message) {
        return new ValidationResult(false, message);
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isNotValid() {
        return !valid;
    }
}

package com.goals.course.journal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FeedbackCannotBeCreated extends RuntimeException {
    public FeedbackCannotBeCreated(final String message) {
        super(message);
    }

    public FeedbackCannotBeCreated(final String message, final Throwable cause) {
        super(message, cause);
    }
}

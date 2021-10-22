package com.goals.course.journal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileCannotBeUploaded extends RuntimeException {
    public FileCannotBeUploaded(final String message) {
        super(message);
    }

    public FileCannotBeUploaded(final String message, final Throwable cause) {
        super(message, cause);
    }
}

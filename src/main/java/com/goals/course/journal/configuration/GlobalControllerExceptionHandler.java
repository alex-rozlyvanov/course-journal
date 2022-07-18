package com.goals.course.journal.configuration;

import com.goals.course.journal.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ServerWebExchange;

import java.time.Clock;
import java.time.ZonedDateTime;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalControllerExceptionHandler {

    private final Clock clock;

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleConflict(final HttpClientErrorException ex,
                                                           final ServerWebExchange exchange) {
        final var errorResponse = buildErrorResponse(ex, exchange, ex.getStatusCode());

        logError(ex, exchange);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalStateException(final IllegalStateException ex,
                                                                        final ServerWebExchange exchange) {
        final var errorResponse = buildErrorResponse(ex, exchange, HttpStatus.BAD_REQUEST);

        logError(ex, exchange);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(final Exception ex,
                                             final ServerWebExchange exchange,
                                             final HttpStatus httpStatus) {
        final var path = exchange.getRequest().getURI().toString();

        return ErrorResponse.builder()
                .timestamp(ZonedDateTime.now(clock))
                .status(httpStatus.value())
                .error(ex.getMessage())
                .path(path)
                .build();
    }

    private void logError(final Exception e, final ServerWebExchange exchange) {
        log.error("Route: {}. Error message: {}", exchange.getRequest().getURI(), e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(final MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to upload. File is too large!");
    }

}

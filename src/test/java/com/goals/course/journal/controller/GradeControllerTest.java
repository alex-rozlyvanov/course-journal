package com.goals.course.journal.controller;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.service.GradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradeControllerTest {

    @Mock
    private GradeService mockGradeService;
    @InjectMocks
    private GradeController service;

    @Test
    void gradeForLesson_callGradeForLesson() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();

        // WHEN
        service.gradeForLesson(gradeDTO);

        // THEN
        verify(mockGradeService).gradeForLesson(gradeDTO);
    }

    @Test
    void gradeForLesson_checkResult() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();
        when(mockGradeService.gradeForLesson(any())).thenReturn(Mono.just(gradeDTO));

        // WHEN
        final var mono = service.gradeForLesson(null);

        // THEN
        StepVerifier.create(mono)
                .expectNext(gradeDTO)
                .verifyComplete();
    }

}

package com.goals.course.journal.controller.implementation;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.service.GradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradeControllerImplTest {

    @Mock
    private GradeService mockGradeService;
    @InjectMocks
    private GradeControllerImpl service;

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
        when(mockGradeService.gradeForLesson(any())).thenReturn(gradeDTO);

        // WHEN
        final var result = service.gradeForLesson(null);

        // THEN
        assertThat(result).isSameAs(gradeDTO);
    }

}

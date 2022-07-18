package com.goals.course.journal.service;

import com.goals.course.journal.dao.entity.GradeEntity;
import com.goals.course.journal.dao.repository.GradeRepository;
import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.exception.GradeCannotBeCreated;
import com.goals.course.journal.mapper.GradeMapper;
import com.goals.course.journal.service.validation.GradeValidation;
import com.goals.course.journal.service.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {

    @Mock
    private GradeMapper mockGradeMapper;
    @Mock
    private GradeValidation mockGradeValidation;
    @Mock
    private GradeRepository mockGradeRepository;

    @InjectMocks
    private GradeService service;

    @Test
    void gradeForLesson_callValidateGrade() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();

        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.validMono());

        // WHEN
        service.gradeForLesson(gradeDTO);

        // THEN
        verify(mockGradeValidation).validateGrade(gradeDTO);
    }

    @Test
    void gradeForLesson_validationFailed_callValidateGrade() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(Mono.just(ValidationResult.invalid("test message")));

        // WHEN
        final var mono = service.gradeForLesson(null);

        // THEN
        StepVerifier.create(mono)
                .verifyErrorSatisfies(expectedException -> assertThat(expectedException)
                        .isInstanceOf(GradeCannotBeCreated.class)
                        .hasMessage("test message"));
    }

    @Test
    void gradeForLesson_validationPassed_callMapToGradeEntity() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();

        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.validMono());
        when(mockGradeMapper.mapToGrade(any())).thenReturn(new GradeEntity());
        when(mockGradeRepository.save(any())).thenReturn(Mono.just(new GradeEntity()));
        when(mockGradeMapper.mapToGradeDTO(any())).thenReturn(GradeDTO.builder().build());

        // WHEN
        final var mono = service.gradeForLesson(gradeDTO);

        // THEN
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
        verify(mockGradeMapper).mapToGrade(gradeDTO);
    }

    @Test
    void gradeForLesson_validationPassed_callSave() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.validMono());

        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeEntity = new GradeEntity().setId(gradeId);
        when(mockGradeMapper.mapToGrade(any())).thenReturn(gradeEntity);
        when(mockGradeRepository.save(any())).thenReturn(Mono.just(new GradeEntity()));
        when(mockGradeMapper.mapToGradeDTO(any())).thenReturn(GradeDTO.builder().build());

        // WHEN
        final var mono = service.gradeForLesson(null);

        // THEN
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
        verify(mockGradeRepository).save(gradeEntity);
    }

    @Test
    void gradeForLesson_validationPassed_call() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.validMono());
        when(mockGradeMapper.mapToGrade(any())).thenReturn(new GradeEntity());

        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var savedGradeEntity = new GradeEntity().setId(gradeId);
        when(mockGradeRepository.save(any())).thenReturn(Mono.just(savedGradeEntity));
        when(mockGradeMapper.mapToGradeDTO(any())).thenReturn(GradeDTO.builder().build());

        // WHEN
        final var mono = service.gradeForLesson(null);

        // THEN
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
        verify(mockGradeMapper).mapToGradeDTO(savedGradeEntity);
    }

    @Test
    void gradeForLesson_validationPassed_checkResult() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.validMono());
        when(mockGradeMapper.mapToGrade(any())).thenReturn(new GradeEntity());
        when(mockGradeRepository.save(any())).thenReturn(Mono.just(new GradeEntity()));

        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();
        when(mockGradeMapper.mapToGradeDTO(any())).thenReturn(gradeDTO);

        // WHEN
        final var mono = service.gradeForLesson(null);

        // THEN
        StepVerifier.create(mono)
                .expectNext(gradeDTO)
                .verifyComplete();
    }

}

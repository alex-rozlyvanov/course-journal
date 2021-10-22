package com.goals.course.journal.service.implementation;

import com.goals.course.journal.dao.entity.GradeEntity;
import com.goals.course.journal.dao.repository.GradeRepository;
import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.exception.GradeCannotBeCreated;
import com.goals.course.journal.mapper.GradeMapper;
import com.goals.course.journal.service.validation.GradeValidation;
import com.goals.course.journal.service.validation.implementation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradeServiceImplTest {

    @Mock
    private GradeMapper mockGradeMapper;
    @Mock
    private GradeValidation mockGradeValidation;
    @Mock
    private GradeRepository mockGradeRepository;

    @InjectMocks
    private GradeServiceImpl service;

    @Test
    void gradeForLesson_callValidateGrade() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();

        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.valid());

        // WHEN
        service.gradeForLesson(gradeDTO);

        // THEN
        verify(mockGradeValidation).validateGrade(gradeDTO);
    }

    @Test
    void gradeForLesson_validationFailed_callValidateGrade() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.invalid("test message"));

        // WHEN
        final var expectedException = assertThrows(GradeCannotBeCreated.class, () -> service.gradeForLesson(null));

        // THEN
        assertThat(expectedException.getMessage()).isEqualTo("test message");
    }

    @Test
    void gradeForLesson_validationPassed_callMapToGradeEntity() {
        // GIVEN
        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();

        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.valid());

        // WHEN
        service.gradeForLesson(gradeDTO);

        // THEN
        verify(mockGradeMapper).mapToGrade(gradeDTO);
    }

    @Test
    void gradeForLesson_validationPassed_callSave() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.valid());

        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeEntity = new GradeEntity().setId(gradeId);
        when(mockGradeMapper.mapToGrade(any())).thenReturn(gradeEntity);

        // WHEN
        service.gradeForLesson(null);

        // THEN
        verify(mockGradeRepository).save(gradeEntity);
    }

    @Test
    void gradeForLesson_validationPassed_call() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.valid());

        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var savedGradeEntity = new GradeEntity().setId(gradeId);
        when(mockGradeRepository.save(any())).thenReturn(savedGradeEntity);

        // WHEN
        service.gradeForLesson(null);

        // THEN
        verify(mockGradeMapper).mapToGradeDTO(savedGradeEntity);
    }

    @Test
    void gradeForLesson_validationPassed_checkResult() {
        // GIVEN
        when(mockGradeValidation.validateGrade(any())).thenReturn(ValidationResult.valid());

        final var gradeId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var gradeDTO = GradeDTO.builder().id(gradeId).build();
        when(mockGradeMapper.mapToGradeDTO(any())).thenReturn(gradeDTO);

        // WHEN
        final var result = service.gradeForLesson(null);

        // THEN
        assertThat(result).isSameAs(gradeDTO);
    }

}

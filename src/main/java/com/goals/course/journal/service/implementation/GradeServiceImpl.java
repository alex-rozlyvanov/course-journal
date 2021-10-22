package com.goals.course.journal.service.implementation;

import com.goals.course.journal.dao.repository.GradeRepository;
import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.exception.GradeCannotBeCreated;
import com.goals.course.journal.exception.GradeNotFoundException;
import com.goals.course.journal.mapper.GradeMapper;
import com.goals.course.journal.service.GradeService;
import com.goals.course.journal.service.validation.GradeValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeValidation gradeValidation;
    private final GradeMapper gradeMapper;
    private final GradeRepository gradeRepository;

    @Override
    @Transactional
    public GradeDTO gradeForLesson(final GradeDTO gradeDTO) {
        log.info("Creating grade {}", gradeDTO);
        final var validationResult = gradeValidation.validateGrade(gradeDTO);

        if (validationResult.isNotValid()) {
            throw new GradeCannotBeCreated(validationResult.getMessage());
        }

        final var mappedGradeEntity = gradeMapper.mapToGrade(gradeDTO);
        final var savedGradeEntry = gradeRepository.save(mappedGradeEntity);

        return gradeMapper.mapToGradeDTO(savedGradeEntry);
    }

    @Override
    public GradeDTO getGradeByLessonIdAndStudentId(final UUID lessonId, final UUID studentId) {
        return gradeRepository.findByLessonIdAndStudentId(lessonId, studentId)
                .map(gradeMapper::mapToGradeDTO)
                .orElseThrow(() -> new GradeNotFoundException("Grade by lesson '%s' and student '%s' not found".formatted(lessonId, studentId)));
    }
}

package com.goals.course.journal.service;

import com.goals.course.journal.dao.repository.GradeRepository;
import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.exception.GradeCannotBeCreated;
import com.goals.course.journal.exception.GradeNotFoundException;
import com.goals.course.journal.mapper.GradeMapper;
import com.goals.course.journal.service.validation.GradeValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class GradeService {
    private final GradeValidation gradeValidation;
    private final GradeMapper gradeMapper;
    private final GradeRepository gradeRepository;

    @Transactional
    public Mono<GradeDTO> gradeForLesson(final GradeDTO gradeDTO) {
        log.info("Creating grade {}", gradeDTO);

        return gradeValidation.validateGrade(gradeDTO)
                .map(validationResult -> {
                    if (validationResult.isNotValid()) {
                        throw new GradeCannotBeCreated(validationResult.getMessage());
                    }
                    return gradeMapper.mapToGrade(gradeDTO);
                })
                .flatMap(gradeRepository::save)
                .map(gradeMapper::mapToGradeDTO);
    }

    public Mono<GradeDTO> getGradeByLessonIdAndStudentId(final UUID lessonId, final UUID studentId) {
        return gradeRepository.findByLessonIdAndStudentId(lessonId, studentId)
                .map(gradeMapper::mapToGradeDTO)
                .switchIfEmpty(Mono.error(() -> new GradeNotFoundException("Grade by lesson '%s' and student '%s' not found".formatted(lessonId, studentId))));
    }
}

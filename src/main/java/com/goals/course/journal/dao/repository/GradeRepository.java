package com.goals.course.journal.dao.repository;

import com.goals.course.journal.dao.entity.GradeEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GradeRepository extends ReactiveSortingRepository<GradeEntity, UUID> {
    Mono<GradeEntity> findByLessonIdAndStudentId(final UUID lessonId, final UUID studentId);
}

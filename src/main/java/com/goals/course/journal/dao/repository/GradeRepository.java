package com.goals.course.journal.dao.repository;

import com.goals.course.journal.dao.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, UUID> {
    Optional<GradeEntity> findByLessonIdAndStudentId(final UUID lessonId, final UUID studentId);
}

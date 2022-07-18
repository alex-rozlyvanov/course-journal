package com.goals.course.journal.controller;

import com.goals.course.journal.dto.GradeDTO;
import com.goals.course.journal.dto.GradeFilters;
import com.goals.course.journal.service.GradeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/journal/grades")
@AllArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PostMapping
    public Mono<GradeDTO> gradeForLesson(@RequestBody final GradeDTO gradeDTO) {
        log.info("Creating grade {}", gradeDTO);
        return gradeService.gradeForLesson(gradeDTO);
    }

    @GetMapping
    public Mono<GradeDTO> getGradeByLessonIdAndStudentId(final GradeFilters gradeFilters) {
        log.info("Getting grade by lesson '{}' and student '{}'", gradeFilters.lessonId(), gradeFilters.studentId());
        return gradeService.getGradeByLessonIdAndStudentId(gradeFilters.lessonId(), gradeFilters.studentId());
    }
}

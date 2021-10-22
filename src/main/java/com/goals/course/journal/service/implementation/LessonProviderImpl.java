package com.goals.course.journal.service.implementation;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.LessonDTO;
import com.goals.course.journal.service.LessonProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@AllArgsConstructor
public class LessonProviderImpl implements LessonProvider {
    private final RestTemplate restTemplate;
    private final ManagerURL managerURL;

    @Override
    public Optional<LessonDTO> findLessonById(final UUID lessonId) {
        log.info("Getting lesson by id '{}'", lessonId);
        final var url = managerURL.lessonsById(lessonId);
        return ofNullable(restTemplate.getForObject(url, LessonDTO.class));
    }
}

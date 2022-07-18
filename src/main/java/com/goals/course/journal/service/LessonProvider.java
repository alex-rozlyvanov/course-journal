package com.goals.course.journal.service;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.LessonDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class LessonProvider {
    private final WebClient webClient;
    private final ManagerURL managerURL;

    public Mono<LessonDTO> findLessonById(final UUID lessonId) {
        log.info("Getting lesson by id '{}'", lessonId);
        final var url = managerURL.lessonsById(lessonId);
        return getForObject(url);
    }

    private Mono<LessonDTO> getForObject(final String url) {
        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatus::isError, r -> Mono.empty())
                    .bodyToMono(LessonDTO.class)
                    .onErrorResume(throwable -> Mono.empty());
        } catch (HttpClientErrorException | WebClientResponseException ex) {
            log.error("Exception during lesson fetch", ex);
            return Mono.empty();
        }
    }

}

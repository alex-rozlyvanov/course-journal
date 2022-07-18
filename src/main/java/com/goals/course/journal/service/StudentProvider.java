package com.goals.course.journal.service;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.UserDTO;
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
public class StudentProvider {
    private final WebClient webClient;
    private final ManagerURL managerURL;

    public Mono<UserDTO> findStudentById(final UUID studentId) {
        log.info("Getting student by id '{}'", studentId);
        final var url = managerURL.studentById(studentId);
        return getForObject(url);
    }

    private Mono<UserDTO> getForObject(final String url) {
        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatus::isError, r -> Mono.empty())
                    .bodyToMono(UserDTO.class)
                    .onErrorResume(throwable -> Mono.empty());
        } catch (HttpClientErrorException | WebClientResponseException ex) {
            log.error("Exception during student fetch", ex);
            return Mono.empty();
        }
    }
}

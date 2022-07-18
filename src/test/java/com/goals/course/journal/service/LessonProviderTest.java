package com.goals.course.journal.service;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.LessonDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonProviderTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient mockWebClient;
    @Mock
    private ManagerURL mockManagerURL;
    @InjectMocks
    private LessonProvider service;

    @Test
    void findLessonById_callLessonById() {
        // GIVEN

        // WHEN
        service.findLessonById(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // THEN
        verify(mockManagerURL).lessonsById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    void findLessonById_callUri() {
        // GIVEN
        when(mockManagerURL.lessonsById(any())).thenReturn("test/url");

        // WHEN
        service.findLessonById(null);

        // THEN
        verify(mockWebClient.get()).uri("test/url");
    }

    @Test
    void findLessonById_checkResult() {
        // GIVEN
        when(mockManagerURL.lessonsById(any())).thenReturn("test/url");
        final var lessonDTO = LessonDTO.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000001")).build();
        when(mockWebClient.get()
                .uri(anyString())
                .retrieve()
                .onStatus(any(), any())
                .bodyToMono(any(Class.class))).thenReturn(Mono.just(lessonDTO));

        // WHEN
        final var mono = service.findLessonById(null);

        // THEN
        StepVerifier.create(mono)
                .expectNext(lessonDTO)
                .verifyComplete();
    }

}

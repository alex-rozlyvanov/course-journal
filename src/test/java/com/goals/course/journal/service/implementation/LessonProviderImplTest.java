package com.goals.course.journal.service.implementation;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.LessonDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonProviderImplTest {
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private ManagerURL mockManagerURL;
    @InjectMocks
    private LessonProviderImpl service;

    @Test
    void findLessonById_callLessonById() {
        // GIVEN

        // WHEN
        service.findLessonById(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // THEN
        verify(mockManagerURL).lessonsById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    void findLessonById_callGetForObject() {
        // GIVEN
        when(mockManagerURL.lessonsById(any())).thenReturn("test/url");

        // WHEN
        service.findLessonById(null);

        // THEN
        verify(mockRestTemplate).getForObject("test/url", LessonDTO.class);
    }

    @Test
    void findLessonById_checkResult() {
        // GIVEN
        when(mockManagerURL.lessonsById(any())).thenReturn("test/url");
        final var lessonDTO = LessonDTO.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000001")).build();
        when(mockRestTemplate.getForObject(anyString(), any())).thenReturn(lessonDTO);

        // WHEN
        final var result = service.findLessonById(null);

        // THEN
        assertThat(result)
                .isPresent()
                .get().isSameAs(lessonDTO);
    }

}

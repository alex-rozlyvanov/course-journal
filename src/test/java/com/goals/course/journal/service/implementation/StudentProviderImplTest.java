package com.goals.course.journal.service.implementation;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.UserDTO;
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
class StudentProviderImplTest {
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private ManagerURL mockManagerURL;
    @InjectMocks
    private StudentProviderImpl service;

    @Test
    void findStudentById_callStudentById() {
        // GIVEN

        // WHEN
        service.findStudentById(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // THEN
        verify(mockManagerURL).studentById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    void findStudentById_callGetForObject() {
        // GIVEN
        when(mockManagerURL.studentById(any())).thenReturn("test/url");

        // WHEN
        service.findStudentById(null);

        // THEN
        verify(mockRestTemplate).getForObject("test/url", UserDTO.class);
    }

    @Test
    void findStudentById_checkResult() {
        // GIVEN
        when(mockManagerURL.studentById(any())).thenReturn("test/url");
        final var studentDTO = UserDTO.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000001")).build();
        when(mockRestTemplate.getForObject(anyString(), any())).thenReturn(studentDTO);

        // WHEN
        final var result = service.findStudentById(null);

        // THEN
        assertThat(result)
                .isPresent()
                .get().isSameAs(studentDTO);
    }

}

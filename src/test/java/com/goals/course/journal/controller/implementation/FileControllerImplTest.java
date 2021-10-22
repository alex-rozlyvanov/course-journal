package com.goals.course.journal.controller.implementation;

import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.dto.UserDTO;
import com.goals.course.journal.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileControllerImplTest {
    @Mock
    private FileService mockFileService;

    @InjectMocks
    private FileControllerImpl service;

    @Test
    void upload_callSave() {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var mockAuthentication = mock(Authentication.class);
        final var userDTO = UserDTO.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000002")).build();
        when(mockAuthentication.getPrincipal()).thenReturn(userDTO);

        // WHEN
        service.upload(mockMultipartFile, lessonId, mockAuthentication);

        // THEN
        verify(mockFileService).save(
                mockMultipartFile,
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"));
    }

    @Test
    void upload_checkResult() {
        // GIVEN
        final var mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(UserDTO.builder().build());

        final var fileResponse = FileResponse.builder().name("testFile").build();
        when(mockFileService.save(any(), any(), any())).thenReturn(fileResponse);

        // WHEN
        final var result = service.upload(null, null, mockAuthentication);

        // THEN
        assertThat(result).isSameAs(fileResponse);
    }
}

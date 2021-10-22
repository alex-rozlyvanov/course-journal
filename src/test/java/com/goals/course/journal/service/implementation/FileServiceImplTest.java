package com.goals.course.journal.service.implementation;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dao.repository.FileRepository;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileRepository mockFileRepository;
    @Mock
    private FileMapper mockFileMapper;

    @InjectMocks
    private FileServiceImpl service;

    @Test
    void save_callMapToFileEntity() {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var userId = UUID.fromString("00000000-0000-0000-0000-000000000002");

        // WHEN
        service.save(mockMultipartFile, lessonId, userId);

        // THEN
        verify(mockFileMapper).mapToFileEntity(mockMultipartFile, lessonId, userId);
    }

    @Test
    void save_callSave() {
        // GIVEN
        final var fileEntity = new FileEntity().setId(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        when(mockFileMapper.mapToFileEntity(any(), any(), any())).thenReturn(fileEntity);

        // WHEN
        service.save(null, null, null);

        // THEN
        verify(mockFileRepository).save(fileEntity);
    }

    @Test
    void save_callMapToFileResponse() {
        // GIVEN
        final var savedFileEntity = new FileEntity().setId(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        when(mockFileRepository.save(any())).thenReturn(savedFileEntity);

        // WHEN
        service.save(null, null, null);

        // THEN
        verify(mockFileMapper).mapToFileResponse(savedFileEntity);
    }

    @Test
    void save_checkResult() {
        // GIVEN
        final var fileResponse = FileResponse.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000003")).build();
        when(mockFileMapper.mapToFileResponse(any())).thenReturn(fileResponse);

        // WHEN
        final var result = service.save(null, null, null);

        // THEN
        assertThat(result).isSameAs(fileResponse);
    }
}

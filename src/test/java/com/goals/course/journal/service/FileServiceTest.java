package com.goals.course.journal.service;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dao.repository.FileRepository;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileRepository mockFileRepository;
    @Mock
    private FileMapper mockFileMapper;

    @InjectMocks
    private FileService service;

    @Test
    void save_callMapToFileEntity() {
        // GIVEN
        when(mockFileMapper.mapToFileEntity(any(), any(), any())).thenReturn(Mono.just(new FileEntity()));

        final var mockFilePart = mock(FilePart.class);
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final var userId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        when(mockFileRepository.save(any())).thenReturn(Mono.just(new FileEntity()));
        when(mockFileMapper.mapToFileResponse(any())).thenReturn(FileResponse.builder().build());

        // WHEN
        final var mono = service.save(mockFilePart, lessonId, userId);

        // THEN
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
        verify(mockFileMapper).mapToFileEntity(mockFilePart, lessonId, userId);
    }

    @Test
    void save_callSave() {
        // GIVEN
        when(mockFileRepository.save(any())).thenReturn(Mono.just(new FileEntity()));

        final var fileEntity = new FileEntity().setId(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        when(mockFileMapper.mapToFileEntity(any(), any(), any())).thenReturn(Mono.just(fileEntity));
        when(mockFileMapper.mapToFileResponse(any())).thenReturn(FileResponse.builder().build());

        // WHEN
        final var mono = service.save(null, null, null);

        // THEN
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
        verify(mockFileRepository).save(fileEntity);
    }

    @Test
    void save_callMapToFileResponse() {
        // GIVEN
        when(mockFileMapper.mapToFileEntity(any(), any(), any())).thenReturn(Mono.just(new FileEntity()));
        final var savedFileEntity = new FileEntity().setId(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        when(mockFileRepository.save(any())).thenReturn(Mono.just(savedFileEntity));
        when(mockFileMapper.mapToFileResponse(any())).thenReturn(FileResponse.builder().build());

        // WHEN
        final var mono = service.save(null, null, null);

        // THEN
        StepVerifier.create(mono).expectNextCount(1).verifyComplete();
        verify(mockFileMapper).mapToFileResponse(savedFileEntity);
    }

    @Test
    void save_checkResult() {
        // GIVEN
        when(mockFileMapper.mapToFileEntity(any(), any(), any())).thenReturn(Mono.just(new FileEntity()));
        when(mockFileRepository.save(any())).thenReturn(Mono.just(new FileEntity()));

        final var fileResponse = FileResponse.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000003")).build();
        when(mockFileMapper.mapToFileResponse(any())).thenReturn(fileResponse);

        // WHEN
        final var mono = service.save(null, null, null);

        // THEN
        StepVerifier.create(mono)
                .expectNext(fileResponse)
                .verifyComplete();
    }
}

package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.exception.FileCannotBeUploaded;
import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileMapperTest {

    @InjectMocks
    private FileMapper service;

    @Test
    void mapToFileResponse_id_checkResult() {
        // GIVEN
        final var fileEntity = new FileEntity()
                .setId(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        // WHEN
        final var result = service.mapToFileResponse(fileEntity);

        // THEN
        assertThat(result.getId()).hasToString("00000000-0000-0000-0000-000000000001");
    }

    @Test
    void mapToFileResponse_name_checkResult() {
        // GIVEN
        final var fileEntity = new FileEntity()
                .setName("test file name");

        // WHEN
        final var result = service.mapToFileResponse(fileEntity);

        // THEN
        assertThat(result.getName()).isEqualTo("test file name");
    }

    @Test
    void mapToFileResponse_size_checkResult() {
        // GIVEN
        final var fileEntity = new FileEntity()
                .setSize(1098246023L);

        // WHEN
        final var result = service.mapToFileResponse(fileEntity);

        // THEN
        assertThat(result.getSize()).isEqualTo(1098246023L);
    }

    @Test
    void mapToFileResponse_contentType_checkResult() {
        // GIVEN
        final var fileEntity = new FileEntity()
                .setContentType("test content type");

        // WHEN
        final var result = service.mapToFileResponse(fileEntity);

        // THEN
        assertThat(result.getContentType()).isEqualTo("test content type");
    }

    @Test
    void mapToFileEntity_name_checkResult() {
        // GIVEN
        final var mockFilePart = mockFilePart("test.pdf");

        // WHEN
        final var mono = service.mapToFileEntity(mockFilePart, null, null);

        // THEN
        StepVerifier.create(mono)
                .assertNext(result -> assertThat(result.getName()).isEqualTo("test.pdf"))
                .verifyComplete();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void mapToFileEntity_nameIsEmpty_checkResult(final String fileName) {
        // GIVEN
        final var mockFilePart = mockFilePart(fileName);

        // WHEN
        final var mono = service.mapToFileEntity(mockFilePart, null, null);

        // THEN
        StepVerifier.create(mono)
                .verifyErrorSatisfies(expectedException -> assertThat(expectedException)
                        .isInstanceOf(FileCannotBeUploaded.class)
                        .hasMessage("Unable to upload file! File should have a name")
                );
    }

    @Test
    void mapToFileEntity_contentType_checkResult() {
        // GIVEN
        final var fileDTO = mockFilePart(new byte[]{1}, "test.xml", MediaType.APPLICATION_ATOM_XML);

        // WHEN
        final var mono = service.mapToFileEntity(fileDTO, null, null);

        // THEN
        StepVerifier.create(mono)
                .assertNext(result -> assertThat(result.getContentType()).isEqualTo(MediaType.APPLICATION_ATOM_XML.toString()))
                .verifyComplete();
    }

    @Test
    void mapToFileEntity_size_checkResult() {
        // GIVEN
        final byte[] bytes = new byte[]{23, 2, 34, 24, 2, 124};
        final var filePart = mockFilePart(bytes, "test.pdf");

        // WHEN
        final var mono = service.mapToFileEntity(filePart, null, null);

        // THEN
        StepVerifier.create(mono)
                .assertNext(result -> assertThat(result.getSize()).isEqualTo(bytes.length))
                .verifyComplete();
    }

    @Test
    void mapToFileEntity_lessonId_checkResult() {
        // GIVEN
        final var filePart = mockFilePart();
        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        // WHEN
        final var mono = service.mapToFileEntity(filePart, lessonId, null);

        // THEN
        StepVerifier.create(mono)
                .assertNext(result -> assertThat(result.getLessonId()).hasToString("00000000-0000-0000-0000-000000000001"))
                .verifyComplete();
    }

    @Test
    void mapToFileEntity_studentId_checkResult() {
        // GIVEN
        final var filePart = mockFilePart();
        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        // WHEN
        final var mono = service.mapToFileEntity(filePart, null, studentId);

        // THEN
        StepVerifier.create(mono)
                .assertNext(result -> assertThat(result.getStudentId()).hasToString("00000000-0000-0000-0000-000000000001"))
                .verifyComplete();
    }

    @Test
    void mapToFileEntity_data_checkResult() {
        // GIVEN
        final byte[] bytes1 = new byte[]{23, 2, 34, 24, 2, 124};
        final byte[] bytes2 = new byte[]{65, 1, 46, 24, 45, 2};
        final byte[] bytes3 = new byte[]{39, 45, 36, 74, 2, 18};
        final var mockFilePart = mock(FilePart.class);
        final var dataBuffer1 = new DefaultDataBufferFactory().wrap(bytes1);
        final var dataBuffer2 = new DefaultDataBufferFactory().wrap(bytes2);
        final var dataBuffer3 = new DefaultDataBufferFactory().wrap(bytes3);
        when(mockFilePart.content()).thenReturn(Flux.just(dataBuffer1, dataBuffer2, dataBuffer3));
        when(mockFilePart.filename()).thenReturn("test.pdf");
        when(mockFilePart.filename()).thenReturn("test.pdf");
        final var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        when(mockFilePart.headers()).thenReturn(httpHeaders);

        // WHEN
        final var mono = service.mapToFileEntity(mockFilePart, null, null);

        // THEN
        StepVerifier.create(mono)
                .assertNext(result -> {
                    final var allBytes = Arrays.concatenate(bytes1, bytes2, bytes3);
                    assertThat(result.getData()).isEqualTo(allBytes);
                })
                .verifyComplete();
    }

    private FilePart mockFilePart(final String filename) {
        return mockFilePart(new byte[]{1, 2, 34, 56, 78}, filename);
    }

    private FilePart mockFilePart() {
        return mockFilePart(new byte[]{1, 2, 34, 56, 78}, "test.pdf");
    }

    private FilePart mockFilePart(final byte[] bytes, final String filename) {
        return mockFilePart(bytes, filename, MediaType.APPLICATION_PDF);
    }

    private FilePart mockFilePart(final byte[] bytes, final String filename, final MediaType mediaType) {
        final var mockFilePart = mock(FilePart.class);
        final var dataBuffer = new DefaultDataBufferFactory().wrap(bytes);
        when(mockFilePart.content()).thenReturn(Flux.just(dataBuffer));
        when(mockFilePart.filename()).thenReturn(filename);

        final var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        lenient().when(mockFilePart.headers()).thenReturn(httpHeaders);

        return mockFilePart;
    }
}

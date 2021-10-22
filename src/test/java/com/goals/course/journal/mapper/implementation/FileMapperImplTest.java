package com.goals.course.journal.mapper.implementation;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.exception.FileCannotBeUploaded;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileMapperImplTest {

    @InjectMocks
    private FileMapperImpl service;

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
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");

        // WHEN
        final var result = service.mapToFileEntity(mockMultipartFile, null, null);

        // THEN
        assertThat(result.getName()).isEqualTo("test.pdf");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void mapToFileEntity_nameIsEmpty_checkResult(final String fileName) {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn(fileName);

        // WHEN
        final var expectedException = assertThrows(
                FileCannotBeUploaded.class,
                () -> service.mapToFileEntity(mockMultipartFile, null, null)
        );

        // THEN
        assertThat(expectedException.getMessage()).isEqualTo("Unable to upload file! File should have a name");
    }

    @Test
    void mapToFileEntity_contentType_checkResult() {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");
        when(mockMultipartFile.getContentType()).thenReturn("test/contentType");

        // WHEN
        final var result = service.mapToFileEntity(mockMultipartFile, null, null);

        // THEN
        assertThat(result.getContentType()).isEqualTo("test/contentType");
    }

    @Test
    void mapToFileEntity_size_checkResult() {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");
        when(mockMultipartFile.getSize()).thenReturn(352352352L);

        // WHEN
        final var result = service.mapToFileEntity(mockMultipartFile, null, null);

        // THEN
        assertThat(result.getSize()).isEqualTo(352352352L);
    }

    @Test
    void mapToFileEntity_lessonId_checkResult() {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");

        final var lessonId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        // WHEN
        final var result = service.mapToFileEntity(mockMultipartFile, lessonId, null);

        // THEN
        assertThat(result.getLessonId()).hasToString("00000000-0000-0000-0000-000000000001");
    }

    @Test
    void mapToFileEntity_studentId_checkResult() {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");

        final var studentId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        // WHEN
        final var result = service.mapToFileEntity(mockMultipartFile, null, studentId);

        // THEN
        assertThat(result.getStudentId()).hasToString("00000000-0000-0000-0000-000000000001");
    }

    @Test
    void mapToFileEntity_data_checkResult() throws IOException {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");

        final byte[] data = new byte[]{23, 2, 34, 24, 2, 124};
        when(mockMultipartFile.getBytes()).thenReturn(data);

        // WHEN
        final var result = service.mapToFileEntity(mockMultipartFile, null, null);

        // THEN
        assertThat(result.getData()).isSameAs(data);
    }

    @Test
    void mapToFileEntity_getBytesThrowsIOException_throwException() throws IOException {
        // GIVEN
        final var mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.pdf");
        final IOException mockIOException = mock(IOException.class);
        when(mockMultipartFile.getBytes()).thenThrow(mockIOException);

        // WHEN
        final var result = assertThrows(
                FileCannotBeUploaded.class,
                () -> service.mapToFileEntity(mockMultipartFile, null, null)
        );

        // THEN
        assertThat(result.getMessage()).isSameAs("Unable to read file bytes!");
        assertThat(result.getCause()).isSameAs(mockIOException);
    }
}

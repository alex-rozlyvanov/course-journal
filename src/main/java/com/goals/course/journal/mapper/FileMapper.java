package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.exception.FileCannotBeUploaded;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.util.StringUtils.hasLength;

@Slf4j
@Component
@AllArgsConstructor
public class FileMapper {

    public FileResponse mapToFileResponse(final FileEntity fileEntity) {
        return FileResponse.builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .size(fileEntity.getSize())
                .contentType(fileEntity.getContentType())
                .build();
    }

    public Mono<FileEntity> mapToFileEntity(final FilePart filePart, final UUID lessonId, final UUID userId) {
        return filePart
                .content()
                .map(this::getBytes)
                .reduce(Arrays::concatenate)
                .map(bytes -> new FileEntity()
                        .setName(getFileName(filePart))
                        .setContentType(filePart.headers().getContentType().toString())
                        .setData(bytes)
                        .setLessonId(lessonId)
                        .setStudentId(userId)
                        .setSize((long) bytes.length));
    }

    private byte[] getBytes(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);

        return bytes;
    }

    private String getFileName(final FilePart filePart) {
        final var filename = filePart.filename();

        if (hasLength(filename)) {
            return filename;
        }

        throw new FileCannotBeUploaded("Unable to upload file! File should have a name");
    }
}

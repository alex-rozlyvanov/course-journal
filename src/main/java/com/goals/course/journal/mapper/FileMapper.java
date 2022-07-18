package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.exception.FileCannotBeUploaded;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public FileEntity mapToFileEntity(final MultipartFile file, final UUID lessonId, final UUID userId) {
        return new FileEntity()
                .setName(getFileName(file))
                .setContentType(file.getContentType())
                .setData(getFileBytes(file))
                .setLessonId(lessonId)
                .setStudentId(userId)
                .setSize(file.getSize());
    }

    private byte[] getFileBytes(final MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new FileCannotBeUploaded("Unable to read file bytes!", e);
        }
    }

    private String getFileName(final MultipartFile file) {
        if (hasLength(file.getOriginalFilename())) {
            return file.getOriginalFilename();
        }

        throw new FileCannotBeUploaded("Unable to upload file! File should have a name");
    }
}

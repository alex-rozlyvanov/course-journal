package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileMapper {
    FileResponse mapToFileResponse(final FileEntity fileEntity);

    FileEntity mapToFileEntity(final MultipartFile file, final UUID lessonId, final UUID userId);
}

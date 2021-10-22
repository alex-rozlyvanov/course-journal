package com.goals.course.journal.service;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {

    FileResponse save(final MultipartFile file, final UUID lessonId, final UUID userId);

    Optional<FileEntity> getFile(final UUID id);

    List<FileEntity> getAllFiles();
}

package com.goals.course.journal.controller;

import com.goals.course.journal.dto.FileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileController {
    FileResponse upload(final MultipartFile file, final UUID lessonId, final Authentication authentication);

    List<FileResponse> list();

    ResponseEntity<byte[]> getFile(final UUID id);
}

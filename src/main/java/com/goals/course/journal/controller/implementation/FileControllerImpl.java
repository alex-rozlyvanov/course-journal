package com.goals.course.journal.controller.implementation;

import com.goals.course.journal.controller.FileController;
import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.dto.UserDTO;
import com.goals.course.journal.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/journal/files")
@AllArgsConstructor
public class FileControllerImpl implements FileController {
    private final FileService fileService;

    @PostMapping
    @Override
    public FileResponse upload(@RequestParam("file") final MultipartFile file,
                               @RequestParam("lesson_id") final UUID lessonId,
                               final Authentication authentication) {
        final var userDetails = (UserDTO) authentication.getPrincipal();
        return fileService.save(file, lessonId, userDetails.getId());
    }

    @GetMapping
    @Override
    public List<FileResponse> list() {
        return fileService.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .toList();
    }

    private FileResponse mapToFileResponse(final FileEntity fileEntity) {
        return FileResponse.builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .contentType(fileEntity.getContentType())
                .size(fileEntity.getSize())
                .build();
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<byte[]> getFile(@PathVariable final UUID id) {
        final var fileEntityOptional = fileService.getFile(id);

        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        final var fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }
}

package com.goals.course.journal.controller;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.dto.UserDTO;
import com.goals.course.journal.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/journal/files")
@AllArgsConstructor
public class FileController {
    private static final Mono<ResponseEntity<byte[]>> NOT_FOUND = Mono.just(ResponseEntity.notFound().build());
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<FileResponse> upload(@RequestPart("file") final FilePart file,
                                     @RequestPart("lesson_id") final String lessonId,
                                     final Authentication authentication) {
        final var userDetails = (UserDTO) authentication.getPrincipal();
        return fileService.save(file, UUID.fromString(lessonId), userDetails.getId());
    }

    @GetMapping
    public Flux<FileResponse> list() {
        return fileService.getAllFiles()
                .map(this::mapToFileResponse);
    }

    private FileResponse mapToFileResponse(final FileEntity fileEntity) {
        return FileResponse.builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .contentType(fileEntity.getContentType())
                .size(fileEntity.getSize())
                .lessonId(fileEntity.getLessonId())
                .build();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<byte[]>> getFile(@PathVariable final UUID id) {
        return fileService.getFile(id)
                .map(fileEntity -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                        .contentType(MediaType.valueOf(fileEntity.getContentType()))
                        .body(fileEntity.getData()))
                .switchIfEmpty(NOT_FOUND);
    }
}

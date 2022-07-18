package com.goals.course.journal.service;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dao.repository.FileRepository;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.mapper.FileMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public Mono<FileResponse> save(final MultipartFile file, final UUID lessonId, final UUID userId) {
        final var fileEntity = fileMapper.mapToFileEntity(file, lessonId, userId);

        return fileRepository.save(fileEntity)
                .map(fileMapper::mapToFileResponse);
    }

    public Mono<FileEntity> getFile(final UUID id) {
        return fileRepository.findById(id);
    }

    public Flux<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }
}

package com.goals.course.journal.service.implementation;

import com.goals.course.journal.dao.entity.FileEntity;
import com.goals.course.journal.dao.repository.FileRepository;
import com.goals.course.journal.dto.FileResponse;
import com.goals.course.journal.mapper.FileMapper;
import com.goals.course.journal.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public FileResponse save(final MultipartFile file, final UUID lessonId, final UUID userId) {
        final var fileEntity = fileMapper.mapToFileEntity(file, lessonId, userId);
        final var savedFileEntity = fileRepository.save(fileEntity);

        return fileMapper.mapToFileResponse(savedFileEntity);
    }

    @Override
    public Optional<FileEntity> getFile(final UUID id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }
}

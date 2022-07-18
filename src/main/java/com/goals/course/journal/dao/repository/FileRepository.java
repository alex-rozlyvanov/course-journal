package com.goals.course.journal.dao.repository;

import com.goals.course.journal.dao.entity.FileEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends ReactiveSortingRepository<FileEntity, UUID> {
}

package com.goals.course.journal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Builder
@Jacksonized
public class FileResponse {
    private final UUID id;
    private final UUID lessonId;
    private final String name;
    private final Long size;
    private final String contentType;
}

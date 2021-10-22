package com.goals.course.journal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@ToString
@Getter
@Builder
@Jacksonized
public class FeedbackDTO {
    private final UUID id;
    private final UUID courseId;
    private final UUID studentId;
    private final String notes;
}

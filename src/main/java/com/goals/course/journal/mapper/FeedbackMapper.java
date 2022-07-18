package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.FeedbackEntity;
import com.goals.course.journal.dto.FeedbackDTO;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackDTO mapToGradeDTO(final FeedbackEntity feedbackEntity) {
        return FeedbackDTO.builder()
                .id(feedbackEntity.getId())
                .studentId(feedbackEntity.getStudentId())
                .courseId(feedbackEntity.getCourseId())
                .notes(feedbackEntity.getNotes())
                .build();
    }

    public FeedbackEntity mapToFeedback(final FeedbackDTO feedbackDTO) {
        return new FeedbackEntity()
                .setId(feedbackDTO.getId())
                .setStudentId(feedbackDTO.getStudentId())
                .setCourseId(feedbackDTO.getCourseId())
                .setNotes(feedbackDTO.getNotes());
    }
}

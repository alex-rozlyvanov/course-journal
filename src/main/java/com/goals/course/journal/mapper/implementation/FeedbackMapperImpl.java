package com.goals.course.journal.mapper.implementation;

import com.goals.course.journal.dao.entity.FeedbackEntity;
import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.mapper.FeedbackMapper;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMapperImpl implements FeedbackMapper {
    @Override
    public FeedbackEntity mapToFeedback(final FeedbackDTO feedbackDTO) {
        return new FeedbackEntity()
                .setId(feedbackDTO.getId())
                .setStudentId(feedbackDTO.getStudentId())
                .setCourseId(feedbackDTO.getCourseId())
                .setNotes(feedbackDTO.getNotes());
    }

    @Override
    public FeedbackDTO mapToGradeDTO(final FeedbackEntity feedbackEntity) {
        return FeedbackDTO.builder()
                .id(feedbackEntity.getId())
                .studentId(feedbackEntity.getStudentId())
                .courseId(feedbackEntity.getCourseId())
                .notes(feedbackEntity.getNotes())
                .build();
    }
}

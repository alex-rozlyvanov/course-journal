package com.goals.course.journal.mapper;

import com.goals.course.journal.dao.entity.FeedbackEntity;
import com.goals.course.journal.dto.FeedbackDTO;

public interface FeedbackMapper {
    FeedbackEntity mapToFeedback(final FeedbackDTO feedbackDTO);

    FeedbackDTO mapToGradeDTO(final FeedbackEntity feedbackEntity);
}

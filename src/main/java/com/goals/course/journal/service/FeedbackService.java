package com.goals.course.journal.service;

import com.goals.course.journal.dto.FeedbackDTO;

public interface FeedbackService {
    FeedbackDTO createFeedback(final FeedbackDTO feedbackDTO);
}

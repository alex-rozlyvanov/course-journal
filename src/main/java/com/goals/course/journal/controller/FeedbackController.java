package com.goals.course.journal.controller;

import com.goals.course.journal.dto.FeedbackDTO;


public interface FeedbackController {
    FeedbackDTO createFeedback(final FeedbackDTO feedbackDTO);
}

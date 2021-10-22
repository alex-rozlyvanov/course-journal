package com.goals.course.journal.controller.implementation;

import com.goals.course.journal.controller.FeedbackController;
import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.service.FeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/journal/feedbacks")
@AllArgsConstructor
public class FeedbackControllerImpl implements FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping
    @Override
    public FeedbackDTO createFeedback(@RequestBody final FeedbackDTO feedbackDTO) {
        log.info("Creating feedback {}", feedbackDTO);
        return feedbackService.createFeedback(feedbackDTO);
    }

}

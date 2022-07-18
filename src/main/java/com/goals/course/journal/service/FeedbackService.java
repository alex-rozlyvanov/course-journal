package com.goals.course.journal.service;

import com.goals.course.journal.dao.repository.FeedbackRepository;
import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.exception.FeedbackCannotBeCreated;
import com.goals.course.journal.mapper.FeedbackMapper;
import com.goals.course.journal.service.validation.FeedbackValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackValidation feedbackValidation;
    private final FeedbackMapper feedbackMapper;

    public Mono<FeedbackDTO> createFeedback(final FeedbackDTO feedbackDTO) {
        return feedbackValidation.validateFeedback(feedbackDTO)
                .map(validationResult -> {
                    if (validationResult.isNotValid()) {
                        throw new FeedbackCannotBeCreated(validationResult.getMessage());
                    }
                    return feedbackMapper.mapToFeedback(feedbackDTO);
                })
                .flatMap(feedbackRepository::save)
                .map(feedbackMapper::mapToGradeDTO);
    }
}

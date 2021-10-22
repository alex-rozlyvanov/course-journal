package com.goals.course.journal.service.implementation;

import com.goals.course.journal.dao.repository.FeedbackRepository;
import com.goals.course.journal.dto.FeedbackDTO;
import com.goals.course.journal.exception.FeedbackCannotBeCreated;
import com.goals.course.journal.mapper.FeedbackMapper;
import com.goals.course.journal.service.FeedbackService;
import com.goals.course.journal.service.validation.FeedbackValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackValidation feedbackValidation;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackDTO createFeedback(final FeedbackDTO feedbackDTO) {
        final var validationResult = feedbackValidation.validateFeedback(feedbackDTO);

        if (validationResult.isNotValid()) {
            throw new FeedbackCannotBeCreated(validationResult.getMessage());
        }

        final var mappedFeedbackEntity = feedbackMapper.mapToFeedback(feedbackDTO);
        final var savedFeedbackEntity = feedbackRepository.save(mappedFeedbackEntity);
        return feedbackMapper.mapToGradeDTO(savedFeedbackEntity);
    }
}

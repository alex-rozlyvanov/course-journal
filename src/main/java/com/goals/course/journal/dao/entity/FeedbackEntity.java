package com.goals.course.journal.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Accessors(chain = true)
@Table("feedbacks")
public class FeedbackEntity {
    @Id
    @Column
    private UUID id;

    @Column
    private UUID courseId;

    @Column
    private UUID studentId;

    @Column
    private String notes;

}

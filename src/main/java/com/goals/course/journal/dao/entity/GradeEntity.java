package com.goals.course.journal.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Accessors(chain = true)
@Table("grades")
public class GradeEntity {
    @Id
    @Column
    private UUID id;

    @Column
    private UUID studentId;

    @Column
    private UUID lessonId;

    @Column
    private Integer grade;

}

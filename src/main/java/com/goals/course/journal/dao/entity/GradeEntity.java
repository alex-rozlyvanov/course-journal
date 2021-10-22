package com.goals.course.journal.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "grades", uniqueConstraints = @UniqueConstraint(columnNames = {"lesson_id", "student_id"}))
public class GradeEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "student_id", updatable = false, nullable = false)
    private UUID studentId;

    @Column(name = "lesson_id", updatable = false, nullable = false)
    private UUID lessonId;

    @Column(nullable = false)
    private Integer grade;

}

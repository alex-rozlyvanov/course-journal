package com.goals.course.journal.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Accessors(chain = true)
@Table("files")
public class FileEntity {

    @Id
    @Column
    private UUID id;

    private String name;

    private String contentType;

    private Long size;

    private UUID lessonId;

    private UUID studentId;

    private byte[] data;

}

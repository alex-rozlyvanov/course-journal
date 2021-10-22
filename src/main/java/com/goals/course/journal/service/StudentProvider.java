package com.goals.course.journal.service;

import com.goals.course.journal.dto.UserDTO;

import java.util.Optional;
import java.util.UUID;

public interface StudentProvider {
    Optional<UserDTO> findStudentById(final UUID studentId);
}

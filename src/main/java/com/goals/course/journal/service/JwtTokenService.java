package com.goals.course.journal.service;

import com.goals.course.journal.dto.UserDTO;

public interface JwtTokenService {
    boolean validate(final String token);

    UserDTO getUserDTO(final String token);
}

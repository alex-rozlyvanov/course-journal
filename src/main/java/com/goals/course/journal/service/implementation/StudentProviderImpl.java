package com.goals.course.journal.service.implementation;

import com.goals.course.journal.configuration.ManagerURL;
import com.goals.course.journal.dto.UserDTO;
import com.goals.course.journal.service.StudentProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@AllArgsConstructor
public class StudentProviderImpl implements StudentProvider {
    private final RestTemplate restTemplate;
    private final ManagerURL managerURL;

    @Override
    public Optional<UserDTO> findStudentById(final UUID studentId) {
        log.info("Getting student by id '{}'", studentId);
        final var url = managerURL.studentById(studentId);
        return ofNullable(restTemplate.getForObject(url, UserDTO.class));
    }
}

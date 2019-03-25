package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.ApplicationAuthenticationService;
import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final int ERROR_CODE_FOR_NON_EXISTING_EMAIL = 7;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ApplicationAuthenticationService authenticationService;

    @Transactional(readOnly = true)
    public List<TaskDto> find(long userId, LocalDate start, LocalDate end) {
        return taskRepository
                .find(userId, start, end)
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> find(long userId) {
        if (!authenticationService.isAllowed(userId)) {
            throw new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                    String.format("Cannot view the given user id '%s'", userId));
        }
        return taskRepository.find(userId)
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> find() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findInformation(long userId) {
        if (!authenticationService.isAllowed(userId)) {
            throw new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                    String.format("Cannot view the given user id '%s'", userId));
        }
        return userRepository.findById(userId)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                        String.format("Cannot find the given user id '%s'", userId)));
    }
}

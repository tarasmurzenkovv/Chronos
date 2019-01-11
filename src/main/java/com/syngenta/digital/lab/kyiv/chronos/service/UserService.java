package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import com.syngenta.digital.lab.kyiv.chronos.service.validation.user.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final int ERROR_CODE_FOR_NULL_BLANK_PASSWORD = 3;
    private static final int ERROR_CODE_FOR_NON_EXISTING_EMAIL = 7;
    private final List<UserValidationService> validationChain;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public UserDto registerUser(UserDto userDto) {
        validationChain.forEach(validator -> validator.validate(userDto));
        UserEntity userEntity = userMapper.mapToEntity(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapToDto(savedUserEntity);
    }

    public UserDto loginRequest(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new UserValidationException(ERROR_CODE_FOR_NULL_BLANK_PASSWORD,
                    "User password cannot be null/blank");
        }
        return userRepository.findByEmail(email)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                        String.format("Cannot find the given email '%s'", email)));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findForUserIdAndDateRange(long userId, LocalDate start, LocalDate end) {
        return taskRepository
                .findAllTasksForUserId(userId, start, end)
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findForUserId(long id) {
        return taskRepository.findAllTasksForUserId(id)
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

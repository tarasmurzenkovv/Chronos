package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.ApplicationAuthenticationService;
import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ResetPasswordRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import com.syngenta.digital.lab.kyiv.chronos.service.validation.user.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private final ApplicationAuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(UserDto userDto) {
        validationChain.forEach(validator -> validator.validate(userDto));
        UserEntity userEntity = userMapper.mapToEntity(userDto);
        userEntity.setUserPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapToDto(savedUserEntity);
    }

    public UserDto login(LoginRequest loginRequest) {
        String password = loginRequest.getPassword();
        String email = loginRequest.getEmail();
        if (StringUtils.isEmpty(password)) {
            throw new UserValidationException(ERROR_CODE_FOR_NULL_BLANK_PASSWORD, "User password cannot be null/blank");
        }

        return userRepository.find(email)
                .map(entity -> {
                    UserDto userDto = userMapper.mapToDto(entity);
                    userDto.setToken(authenticationService.authenticate(email, password));
                    return userDto;
                })
                .orElseThrow(() -> new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                        String.format("Cannot find the given email '%s'", email)));
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String newPassword = resetPasswordRequest.getNewPassword();
        String email = resetPasswordRequest.getEmail();
        if (StringUtils.isEmpty(newPassword)) {
            throw new UserValidationException(ERROR_CODE_FOR_NULL_BLANK_PASSWORD, "User password cannot be null/blank");
        }
        long emails = userRepository.countEmails(email);
        if (emails > 0) {
            String newEncodedPassword = passwordEncoder.encode(newPassword);
            userRepository.updatePassword(newEncodedPassword, email);
        } else {
            throw new UserValidationException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                    String.format("Cannot find the given email '%s'", email));
        }
    }

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

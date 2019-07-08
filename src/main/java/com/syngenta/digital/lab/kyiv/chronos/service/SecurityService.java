package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.configuration.security.service.ApplicationAuthenticationService;
import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ResetPasswordRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ApplicationBaseException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import com.syngenta.digital.lab.kyiv.chronos.service.validation.user.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private static final int ERROR_CODE_FOR_NULL_BLANK_PASSWORD = 3;
    private static final int ERROR_CODE_FOR_NON_EXISTING_EMAIL = 7;
    private final List<UserValidationService> validationChain;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
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
            throw new ApplicationBaseException(ERROR_CODE_FOR_NULL_BLANK_PASSWORD, "User password cannot be null/blank");
        }

        return userRepository.find(email)
                .map(entity -> {
                    UserDto userDto = userMapper.mapToDto(entity);
                    userDto.setToken(authenticationService.authenticate(email, password));
                    return userDto;
                })
                .orElseThrow(() -> new ApplicationBaseException(ERROR_CODE_FOR_NON_EXISTING_EMAIL,
                        String.format("Cannot find the given email '%s'", email)));
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String newPassword = resetPasswordRequest.getNewPassword();
        String email = resetPasswordRequest.getEmail();
        if (StringUtils.isEmpty(newPassword)) {
            throw new ApplicationBaseException(ERROR_CODE_FOR_NULL_BLANK_PASSWORD, "User password cannot be null/blank");
        }
        userRepository.find(email)
                .ifPresentOrElse(user -> {
                    if (!passwordEncoder.matches(resetPasswordRequest.getOldPassword(), user.getUserPassword())) {
                        throw new ApplicationBaseException(ERROR_CODE_FOR_NON_EXISTING_EMAIL, "Old password doesnt match the stored one. ");
                    }
                    String newEncodedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
                    userRepository.updatePassword(newEncodedPassword, email);
                }, () -> {
                    throw new ApplicationBaseException(ERROR_CODE_FOR_NON_EXISTING_EMAIL, String.format("Cannot find the given email '%s'", email));
                });
    }
}

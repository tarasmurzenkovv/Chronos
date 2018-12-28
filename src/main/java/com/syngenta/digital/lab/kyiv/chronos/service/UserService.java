package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.UserMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import com.syngenta.digital.lab.kyiv.chronos.service.validation.user.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final List<UserValidationService> validationChain;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDto registerUser(UserDto userDto) {
        validationChain.forEach(validator -> validator.validate(userDto));
        UserEntity userEntity = userMapper.mapToEntity(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapToDto(savedUserEntity);
    }
}

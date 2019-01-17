package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private static final String PASSWORD_MASK = "*****";
    private static final String DEFAULT_JOB_TITLE = "Developer";
    private final UserRoleRepository userRoleRepository;

    public UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setUserEmail(userDto.getEmail());
        userEntity.setUserPassword(userDto.getPassword());
        userEntity.setUserRoleEntity(userRoleRepository.findByRole("Regular")
                .orElseThrow(() -> new RuntimeException("Cannot locate role in db " + userDto.getRole())));
        userEntity.setJobTitle(StringUtils.isEmpty(userDto.getJobTitle())? DEFAULT_JOB_TITLE : userDto.getJobTitle());
        return userEntity;
    }

    public UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getUserEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setPassword(PASSWORD_MASK);
        userDto.setRole(userEntity.getUserRoleEntity().getRole());
        userDto.setJobTitle(StringUtils.isEmpty(userEntity.getJobTitle())? DEFAULT_JOB_TITLE : userEntity.getJobTitle());

        return userDto;
    }
}

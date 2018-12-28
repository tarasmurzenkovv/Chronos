package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setUserEmail(userDto.getEmail());
        userEntity.setUserPassword(userDto.getPassword());
        return userEntity;
    }

    public UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getUserEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setPassword(userEntity.getUserPassword());

        return userDto;
    }
}

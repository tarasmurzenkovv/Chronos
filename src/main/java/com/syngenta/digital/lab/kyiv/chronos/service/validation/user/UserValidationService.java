package com.syngenta.digital.lab.kyiv.chronos.service.validation.user;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;

public interface UserValidationService {
    void validate(UserDto userDto);
}

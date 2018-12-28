package com.syngenta.digital.lab.kyiv.chronos.service.validation.user;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserFirstAndLastNameValidationService implements UserValidationService {
    private static final int ERROR_CODE_FOR_FIRST_NAME = 1;
    private static final int ERROR_CODE_FOR_LAST_NAME = 2;
    private static final String ERROR_MESSAGE_FOR_FIRST_NAME = "User first name cannot be null/blank";
    private static final String ERROR_MESSAGE_FOR_LAST_NAME = "User last name cannot be null/blank";
    @Override
    public void validate(UserDto userDto) {
        validateFirstName(userDto);
        validateLastName(userDto);
    }

    private void validateFirstName(UserDto userDto) {
        String firstName = userDto.getFirstName();
        if (StringUtils.isEmpty(firstName)) {
            throw new UserValidationException(ERROR_CODE_FOR_FIRST_NAME, ERROR_MESSAGE_FOR_FIRST_NAME);
        }
    }

    private void validateLastName(UserDto userDto) {
        String lastName = userDto.getLastName();
        if (StringUtils.isEmpty(lastName)) {
            throw new UserValidationException(ERROR_CODE_FOR_LAST_NAME, ERROR_MESSAGE_FOR_LAST_NAME);
        }
    }
}

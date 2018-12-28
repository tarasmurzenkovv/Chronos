package com.syngenta.digital.lab.kyiv.chronos.service.validation.user;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserPasswordValidationService implements UserValidationService {
    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    private static final String INVALID_PASSWORD_FORMAT_ERR_MSG = "User password is too weak. It must have minimum eight "
            + "character, at leas one uppercase letter, one lowercase letter, one number and one special character.";
    private static final int ERROR_CODE_FOR_INVALID_PASSWORD_FORMAT = 6;
    private static final int ERROR_CODE_FOR_NULL_BLANK_PASSWORD = 3;
    private static final String ERROR_MESSAGE_FOR_NULL_BLANK_PASSWORD = "User password cannot be null/blank";
    ;


    @Override
    public void validate(UserDto userDto) {
        String password = userDto.getPassword();
        validateIfPasswordIsNullOrBlank(password);
        validatePasswordFormat(password);
    }

    private void validateIfPasswordIsNullOrBlank(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new UserValidationException(ERROR_CODE_FOR_NULL_BLANK_PASSWORD, ERROR_MESSAGE_FOR_NULL_BLANK_PASSWORD);
        }
    }

    private void validatePasswordFormat(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        if (!matcher.matches()) {
            throw new UserValidationException(ERROR_CODE_FOR_INVALID_PASSWORD_FORMAT, INVALID_PASSWORD_FORMAT_ERR_MSG);
        }
    }
}

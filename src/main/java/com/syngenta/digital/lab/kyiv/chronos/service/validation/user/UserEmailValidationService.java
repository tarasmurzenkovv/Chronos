package com.syngenta.digital.lab.kyiv.chronos.service.validation.user;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserEmailValidationService implements UserValidationService {
    private static final int ERROR_CODE_EMAIL_UNIQUENESS = 5;
    private static final int ERROR_CODE_EMAIL_INVALID_FORMAT = 4;
    private static final int ERROR_CODE_EMAIL_EMAIL_IS_NULL_BLANK = 3;
    private static final String ERROR_MESSAGE_EMPTY_OR_NULL_EMAIL = "User email cannot be null/blank";
    private static final String EMAIL_UNIQUENESS_ERROR_MESSAGE = "User email '%s' is already registered in the system";
    private static final String ERROR_CODE_EMAIL_INVALID_FORMAT_MESSAGE =  "User email '%s' has the invalid format";
    private static final Pattern EMAIL_REGEX = Pattern.compile("^(.+)@(.+)$");

    private final UserRepository userRepository;

    @Override
    public void validate(UserDto userDto) {
        String email = userDto.getEmail();
        validateEmailUniqueness(email);
        validateIfEmailsIsNullOrEmpty(email);
        validateEmailRegex(email);
    }

    private void validateEmailRegex(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        if (!matcher.matches()) {
            String errorMessage = String.format(ERROR_CODE_EMAIL_INVALID_FORMAT_MESSAGE, email);
            throw new UserValidationException(ERROR_CODE_EMAIL_INVALID_FORMAT, errorMessage);
        }
    }

    private void validateIfEmailsIsNullOrEmpty(String email) {
        if (StringUtils.isEmpty(email)) {

            throw new UserValidationException(ERROR_CODE_EMAIL_EMAIL_IS_NULL_BLANK, ERROR_MESSAGE_EMPTY_OR_NULL_EMAIL);
        }
    }

    private void validateEmailUniqueness(String email) {
        Long emailsCount = userRepository.countEmails(email);

        if (emailsCount > 0) {
            String errorMessage = String.format(EMAIL_UNIQUENESS_ERROR_MESSAGE, email);
            throw new UserValidationException(ERROR_CODE_EMAIL_UNIQUENESS, errorMessage);
        }
    }
}

package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserRoleEnum;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ApplicationBaseException;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

public class UserRoleConverter implements AttributeConverter<UserRoleEnum, String> {
    private static final int ERROR_CODE_FOR_NON_EXISTING_ENUM = 7;

    @Override
    public String convertToDatabaseColumn(UserRoleEnum attribute) {
        return attribute.getRoleAsString();
    }

    @Override
    public UserRoleEnum convertToEntityAttribute(String dbData) {
        return Arrays.stream(UserRoleEnum.values())
                .filter(userRoleEnum -> userRoleEnum.getRoleAsString().equalsIgnoreCase(dbData))
                .findFirst()
                .orElseThrow(() -> new ApplicationBaseException(ERROR_CODE_FOR_NON_EXISTING_ENUM, "Cannot find user role for provided value " + dbData));
    }
}

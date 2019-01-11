package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

public class UserValidationException extends ApplicationBaseException {
    public UserValidationException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

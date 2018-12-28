package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserValidationException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
}

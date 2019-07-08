package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplicationBaseException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
}

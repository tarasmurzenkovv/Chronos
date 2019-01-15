package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

public class TaskException extends ApplicationBaseException {
    public TaskException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

public class ProjectException extends ApplicationBaseException {
    public ProjectException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

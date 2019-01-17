package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

public class ReportingException extends ApplicationBaseException {
    public ReportingException(int errorCode, String message) {
        super(errorCode, message);
    }
}

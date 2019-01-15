package com.syngenta.digital.lab.kyiv.chronos.model.exceptions;

public class TagException extends ApplicationBaseException{
    public TagException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

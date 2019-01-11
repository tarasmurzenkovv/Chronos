package com.syngenta.digital.lab.kyiv.chronos.exception.handlers;

import com.syngenta.digital.lab.kyiv.chronos.controllers.UserController;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.UserValidationException;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvise {

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<GeneralResponse<ErrorResponsePayload>> handleUserValidationErrors(UserValidationException userValidationException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponse.buildErrorResponse(ErrorResponsePayload.from(userValidationException)));
    }
}

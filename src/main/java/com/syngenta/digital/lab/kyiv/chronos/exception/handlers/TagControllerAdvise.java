package com.syngenta.digital.lab.kyiv.chronos.exception.handlers;

import com.syngenta.digital.lab.kyiv.chronos.controllers.TagsController;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TagException;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = TagsController.class)
public class TagControllerAdvise {

    @ExceptionHandler(TagException.class)
    public ResponseEntity<GeneralResponse<ErrorResponsePayload>> handleUserValidationErrors(TagException tagException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponse.buildErrorResponse(ErrorResponsePayload.from(tagException)));
    }
}

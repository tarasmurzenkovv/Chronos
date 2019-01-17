package com.syngenta.digital.lab.kyiv.chronos.exception.handlers;

import com.syngenta.digital.lab.kyiv.chronos.controllers.ReportingController;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = ReportingController.class)
public class ReportingControllerAdvise {

    @ExceptionHandler(ReportingException.class)
    public ResponseEntity<GeneralResponse<ErrorResponsePayload>> handleUserValidationErrors(ReportingException reportingException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponse.buildErrorResponse(ErrorResponsePayload.from(reportingException)));
    }
}

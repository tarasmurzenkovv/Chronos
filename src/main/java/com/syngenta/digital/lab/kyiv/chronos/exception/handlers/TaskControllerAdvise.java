package com.syngenta.digital.lab.kyiv.chronos.exception.handlers;

import com.syngenta.digital.lab.kyiv.chronos.controllers.TaskController;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TaskException;
import com.syngenta.digital.lab.kyiv.chronos.model.response.ErrorResponsePayload;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = TaskController.class)
public class TaskControllerAdvise {
    @ExceptionHandler(TaskException.class)
    public ResponseEntity<GeneralResponse<ErrorResponsePayload>> handleUProjectTypeException(TaskException projectTypeException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GeneralResponse.buildErrorResponse(ErrorResponsePayload.from(projectTypeException)));
    }
}

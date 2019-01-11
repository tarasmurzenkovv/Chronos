package com.syngenta.digital.lab.kyiv.chronos.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ApplicationBaseException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponsePayload {
    @JsonProperty("error_code")
    private int errorCode;
    @JsonProperty("error_message")
    private String errorMessage;

    public static <T extends ApplicationBaseException> ErrorResponsePayload from(T exception) {
        return new ErrorResponsePayload(exception.getErrorCode(), exception.getErrorMessage());
    }
}

package com.syngenta.digital.lab.kyiv.chronos.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}

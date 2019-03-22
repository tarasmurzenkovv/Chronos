package com.syngenta.digital.lab.kyiv.chronos.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GeneralResponse<T> {
    private boolean error;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

    public static <T> ResponseEntity<GeneralResponse<T>> from(HttpStatus status) {
        GeneralResponse<T> tGeneralResponse = GeneralResponse.buildResponse();
        return ResponseEntity
                .status(status)
                .body(tGeneralResponse);
    }

    public static <T> ResponseEntity<GeneralResponse<T>> from(T data, HttpStatus status) {
        GeneralResponse<T> tGeneralResponse = GeneralResponse.buildResponse(data);
        return ResponseEntity
                .status(status)
                .body(tGeneralResponse);
    }

    private static <T> GeneralResponse<T> buildResponse() {
        return new GeneralResponse<>(false, null);
    }

    private static <T> GeneralResponse<T> buildResponse(T data) {
        return new GeneralResponse<>(false, data);
    }

    public static <T> GeneralResponse<T> buildErrorResponse(T data) {
        return new GeneralResponse<>(true, data);
    }
}

package com.syngenta.digital.lab.kyiv.chronos.model.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GeneralResponse<T> {
    private boolean error;
    private T data;

    public static <T> GeneralResponse<T> buildResponse(T data) {
        return new GeneralResponse<>(false, data);
    }

    public static <T> GeneralResponse<T> buildErrorResponse(T data) {
        return new GeneralResponse<>(true, data);
    }
}

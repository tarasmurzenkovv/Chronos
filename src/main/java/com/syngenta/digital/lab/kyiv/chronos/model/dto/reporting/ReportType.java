package com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting;

import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ReportingException;

import java.util.Arrays;

public enum ReportType {

    XLS("xls"), CSV("csv");

    private final String typeAsString;

    ReportType(String typeAsString) {
        this.typeAsString = typeAsString;
    }

    public static ReportType from(String typeAsString) {
        int errorCode = 17;
        return Arrays.stream(ReportType.values())
                .filter(value -> typeAsString.equalsIgnoreCase(value.typeAsString))
                .findFirst()
                .orElseThrow(() -> new ReportingException(errorCode, "No suitable report type is provided"));
    }
}

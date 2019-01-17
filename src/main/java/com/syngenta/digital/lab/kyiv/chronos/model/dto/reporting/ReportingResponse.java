package com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReportingResponse {
    private String fileName;
    private byte[] content;
}

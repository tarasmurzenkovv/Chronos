package com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Range {
    private LocalDate start;
    private LocalDate end;
}

package com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportingRequest {
    @JsonProperty("reporting_range")
    private Range range;
    @JsonProperty("user_ids")
    private List<Long> userIds;
}

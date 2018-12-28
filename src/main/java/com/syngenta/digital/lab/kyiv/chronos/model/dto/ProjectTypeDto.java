package com.syngenta.digital.lab.kyiv.chronos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectTypeDto {

    private Long id;
    @JsonProperty("project_type")
    private String projectType;
}

package com.syngenta.digital.lab.kyiv.chronos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("project_description")
    private String projectDescription;
    @JsonProperty("project_type_id")
    private Long projectTypeId;
}

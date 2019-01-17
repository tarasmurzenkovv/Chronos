package com.syngenta.digital.lab.kyiv.chronos.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDto {
    @JsonProperty("task_id")
    private Long taskId;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("project_id")
    private long projectId;

    @ApiModelProperty(notes = "String representation of the date in format 'dd/MM/yyyy'")
    @JsonProperty("reporting_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate reportingDate;
    @JsonProperty("spent_time")
    private float spentTime;
    @JsonProperty("tags")
    private List<TagDto> tags;
    @JsonProperty("comments")
    private String comments;
}

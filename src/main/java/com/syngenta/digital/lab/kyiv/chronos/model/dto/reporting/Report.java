package com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Report {
    private Long taskId;
    private String projectName;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private Long spentTime;
    private LocalDate reportingDate;
    private String comments;
}

package com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @CsvBindByName(column = "Project name")
    private String projectName;
    @CsvBindByName(column = "Spent time")
    private Long spentTime;
    @CsvBindByName(column = "First name")
    private String firstName;
    @CsvBindByName(column = "Last name")
    private String lastName;

}

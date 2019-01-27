package com.syngenta.digital.lab.kyiv.chronos.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "reporting")
@Getter
@Setter
public class ReportingProperties {
    private String dateFormat;
    private String dateTimeFormat;
    private String dateFileNameFormat;
}

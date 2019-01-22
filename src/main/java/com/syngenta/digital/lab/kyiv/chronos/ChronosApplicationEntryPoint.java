package com.syngenta.digital.lab.kyiv.chronos;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ReportingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SuppressWarnings("all")
@SpringBootApplication
public class ChronosApplicationEntryPoint {
    public static void main(String[] args) {
        SpringApplication.run(ChronosApplicationEntryPoint.class, args);
    }
}

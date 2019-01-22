package com.syngenta.digital.lab.kyiv.chronos.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ClockService {

    public LocalDate now() {
        return LocalDate.now();
    }

    public LocalDateTime nowTime() {
        return LocalDateTime.now();
    }
}

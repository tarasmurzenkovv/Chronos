package com.syngenta.digital.lab.kyiv.chronos.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {
    private DateTimeUtils() {}

    public static String format(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }
}

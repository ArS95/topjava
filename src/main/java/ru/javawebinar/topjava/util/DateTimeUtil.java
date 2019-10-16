package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetweenTime(LocalDateTime lt, T startTime, T endTime) {
        if (startTime instanceof LocalDate) {
            return lt.toLocalDate().compareTo((ChronoLocalDate) startTime) >= 0 && lt.toLocalDate().compareTo((ChronoLocalDate) endTime) <= 0;
        } else if (startTime instanceof LocalTime) {
            return lt.toLocalTime().compareTo((LocalTime) startTime) >= 0 && lt.toLocalTime().compareTo((LocalTime) endTime) <= 0;
        }
        throw new IllegalArgumentException("arguments must be instanceof LocalDate or LocalTime");
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}


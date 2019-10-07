package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd'T'HH:mm";
    private static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM));
    }

    public static LocalDateTime toLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM);
        return LocalDateTime.parse(date, formatter);
    }
}

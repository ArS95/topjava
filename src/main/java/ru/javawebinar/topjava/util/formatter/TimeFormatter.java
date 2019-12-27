package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return !text.isBlank() ? LocalTime.parse(text) : null;
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return null;
    }
}

package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return !text.isBlank() ? LocalDate.parse(text) : null;
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return null;
    }
}

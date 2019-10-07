package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class TestData {
    public static List<Meal> TEST_DATA_LIST = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2016, Month.MAY, 31, 8, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017, Month.MAY, 2, 7, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017, Month.MAY, 2, 1, 0), "Обед", 1600),
            new Meal(LocalDateTime.of(2017, Month.MAY, 1, 10, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2018, Month.MAY, 3, 11, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2018, Month.MAY, 3, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2018, Month.MAY, 3, 20, 0), "Ужин", 510));
}

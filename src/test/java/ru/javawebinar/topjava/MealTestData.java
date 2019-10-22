package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int ID_1 = START_SEQ + 2;

    public static final Meal MEAL_1 = new Meal(ID_1, LocalDateTime.of(2015, Month.OCTOBER, 20, 10, 00), "breakfast", 500);
    public static final Meal MEAL_2 = new Meal(ID_1 + 1, LocalDateTime.of(2016, Month.OCTOBER, 20, 10, 10), "lunch", 550);
    public static final Meal MEAL_3 = new Meal(ID_1 + 2, LocalDateTime.of(2017, Month.OCTOBER, 21, 10, 20), "dinner", 600);
    public static final Meal MEAL_4 = new Meal(ID_1 + 3, LocalDateTime.of(2019, Month.OCTOBER, 21, 10, 30), "dinner", 650);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
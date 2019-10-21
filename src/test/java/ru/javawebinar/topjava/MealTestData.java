package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int ID_1 = START_SEQ + 2;
    public static final int ID_2 = ID_1 + 1;
    public static final int ID_3 = ID_2 + 1;
    public static final int ID_4 = ID_3 + 1;

    public static final LocalDateTime DATE_TIME_1 = LocalDateTime.of(2019, Month.OCTOBER, 20, 10, 00);
    public static final LocalDateTime DATE_TIME_2 = LocalDateTime.of(2019, Month.OCTOBER, 20, 10, 10);
    public static final LocalDateTime DATE_TIME_3 = LocalDateTime.of(2019, Month.OCTOBER, 21, 10, 20);
    public static final LocalDateTime DATE_TIME_4 = LocalDateTime.of(2019, Month.OCTOBER, 21, 10, 30);

    public static final int CALORIES_1 = 500;
    public static final int CALORIES_2 = CALORIES_1 + 50;
    public static final int CALORIES_3 = CALORIES_2 + 50;
    public static final int CALORIES_4 = CALORIES_3 + 50;

    public static final String DESCRIPTION_1 = "breakfast";
    public static final String DESCRIPTION_2 = "lunch";
    public static final String DESCRIPTION_3 = "dinner";

    public static final Meal MEAL_1 = new Meal(ID_1, DATE_TIME_1, DESCRIPTION_1, CALORIES_1);
    public static final Meal MEAL_2 = new Meal(ID_2, DATE_TIME_2, DESCRIPTION_2, CALORIES_2);
    public static final Meal MEAL_3 = new Meal(ID_3, DATE_TIME_3, DESCRIPTION_3, CALORIES_3);
    public static final Meal MEAL_4 = new Meal(ID_4, DATE_TIME_4, DESCRIPTION_3, CALORIES_4);

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
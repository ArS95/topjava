package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS;

    static {
        Meal meal1 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        meal1.setUserId(1);
        Meal meal2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        meal2.setUserId(1);
        Meal meal3 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        meal3.setUserId(1);
        Meal meal4 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        meal4.setUserId(1);
        Meal meal5 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        meal5.setUserId(1);
        Meal meal6 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        meal6.setUserId(1);

        MEALS = Arrays.asList(
                meal1,
                meal2,
                meal3,
                meal4,
                meal5,
                meal6
        );
    }

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFiltered(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenTime(meal.getTime(), startTime, endTime));
    }

    private static List<MealTo> getFiltered(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
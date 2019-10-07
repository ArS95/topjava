package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealsUtil {
    private static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getFilteredByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsTo = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsTo.add(createTo(meal, caloriesSumByDate.get(meal.getDate()) > DEFAULT_CALORIES_PER_DAY));
            }
        });
        return mealsTo;
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }
}
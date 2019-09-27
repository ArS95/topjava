package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededAndUseStreamApi(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        mealList.forEach(uM -> {
            dateCaloriesMap.merge(toLocalDate(uM), uM.getCalories(), Integer::sum);
        });

        List<UserMealWithExceed> resultList = new ArrayList<>();
        mealList.forEach(uM -> {
            if (TimeUtil.isBetween(toLocalTime(uM), startTime, endTime)) {
                resultList.add(createUserMealWithExceed(uM, dateCaloriesMap.get(toLocalDate(uM)) > caloriesPerDay));
            }
        });
        return resultList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededAndUseStreamApi(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> sumMap = mealList.stream()
                .collect(
                        Collectors.groupingBy(UserMealsUtil::toLocalDate,
                                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(uM -> TimeUtil.isBetween(toLocalTime(uM), startTime, endTime))
                .map(uM -> createUserMealWithExceed(uM, sumMap.get(uM.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExceed createUserMealWithExceed(UserMeal userMeal, boolean exceed) {
        return new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }

    private static LocalDate toLocalDate(UserMeal userMeal) {
        return userMeal.getDateTime().toLocalDate();
    }

    private static LocalTime toLocalTime(UserMeal userMeal) {
        return userMeal.getDateTime().toLocalTime();
    }
}


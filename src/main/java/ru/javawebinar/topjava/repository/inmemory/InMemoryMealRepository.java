package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> this.save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer mealId = meal.getId();

        Map<Integer, Meal> mealMap = getMealMap(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);

            if (mealMap != null) {
                return mealMap.put(mealId, meal);
            } else {
                Map<Integer, Meal> hashMap = new HashMap<>();
                hashMap.put(mealId, meal);
                repository.put(userId, hashMap);
                return meal;
            }
        }
        // treat case: update, but not present in storage
        if (mealMap != null) {
            Meal repMeal = mealMap.get(mealId);
            if (repMeal != null && repMeal.getUserId() == userId) {
                return mealMap.put(mealId, meal);
            }
        }
        return null;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> mealMap = getMealMap(userId);
        return mealMap != null && mealMap.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> mealMap = getMealMap(userId);
        return mealMap == null ? null : mealMap.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> mealMap = getMealMap(userId);
        return mealMap == null ? Collections.emptyList() : mealMap.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed().thenComparing(Meal::getTime).reversed())
                .collect(Collectors.toList());
    }

    private Map<Integer, Meal> getMealMap(int userId) {
        return repository.get(userId);
    }
}


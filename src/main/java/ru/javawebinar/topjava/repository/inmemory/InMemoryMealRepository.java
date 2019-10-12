package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

//    {
//        MealsUtil.MEALS.forEach(this::save);
//    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());

            if (repository.get(meal.getUserId()) != null) {
                return repository.get(meal.getUserId()).put(meal.getId(), meal);
            } else {
                Map<Integer, Meal> mealMap = new HashMap<>();
                mealMap.put(meal.getId(), meal);
                repository.put(meal.getUserId(), mealMap);
                return meal;
            }
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            oldMeal.put(meal.getId(), meal);
            return oldMeal;
        }).get(meal.getId());
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? null : mealMap.get(mealId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? Collections.emptyList() : mealMap.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}


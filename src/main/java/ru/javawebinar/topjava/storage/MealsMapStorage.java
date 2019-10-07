package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMapStorage implements Storage {
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(MealsMapStorage.class);
    private static final AtomicInteger id = new AtomicInteger();

    @Override
    public Meal update(Meal meal) {
        log.info("UPDATE: " + meal);
        storage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal add(Meal meal) {
        log.info("ADD: " + meal);
        meal.setId(id.getAndIncrement());
        storage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        log.info("GET: " + id);
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        log.info("DELETE: " + id);
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        log.info("GetALL");
        return new ArrayList<>(storage.values());
    }
}
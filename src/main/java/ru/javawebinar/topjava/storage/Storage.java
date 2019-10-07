package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    Meal update(Meal meal);

    Meal add(Meal meal);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();

}

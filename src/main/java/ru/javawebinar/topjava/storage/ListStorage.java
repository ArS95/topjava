package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListStorage implements Storage {
    private List<Meal> storage = new CopyOnWriteArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(ListStorage.class);

    @Override
    public void clear() {
        log.info("CLEAR");
        storage.clear();
    }

    @Override
    public void update(Meal meal) {
        log.info("UPDATE: " + meal);
        storage.remove(meal.getId());
        add(meal);
    }

    @Override
    public void add(Meal meal) {
        log.info("ADD: " + meal);
        storage.add(meal);
    }


    @Override
    public Meal get(int id) {
        log.info("GET: " + id);
        return storage.get(getSearchKey(storage, id));
    }

    @Override
    public void delete(int id) {
        log.info("DELETE: " + id);
        storage.remove(getSearchKey(storage, id));
    }

    @Override
    public List<Meal> getAll() {
        log.info("GetALL");
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        log.info("SIZE");
        return storage.size();
    }

    private int getSearchKey(List<Meal> storage, int id) {
        int key = -1;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == id) {
                key = i;
                break;
            }
        }
        return key;
    }
}

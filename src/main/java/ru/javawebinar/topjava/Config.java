package ru.javawebinar.topjava;

import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }
    private Config(){
        storage = new MapStorage();
    }

    public Storage getStorage() {
        return storage;
    }
}

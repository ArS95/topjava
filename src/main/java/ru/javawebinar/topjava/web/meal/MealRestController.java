package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId, authUserId());
    }

    public Meal update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        return service.update(meal, authUserId());
    }

    public List<MealTo> getAllTo() {
        log.info("getAllTo");
        return MealsUtil.getTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFilteredByDate(LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered");
        return MealsUtil.getFilteredTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startDate, endDate);
    }

    public List<MealTo> getAllFilteredByTime(LocalTime startTime, LocalTime endTime) {
        log.info("getAllFiltered");
        return MealsUtil.getFilteredTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }
}
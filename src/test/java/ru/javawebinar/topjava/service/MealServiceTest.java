package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(ID_1, USER_ID), MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getAlienMeal() {
        assertMatch(service.get(ID_1, ADMIN_ID), MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(ID_1, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlienMeal() {
        service.delete(ID_1, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(LocalDate.of(2019, Month.OCTOBER, 20), LocalDate.of(2019, Month.OCTOBER, 21), USER_ID), MEAL_1, MEAL_2, MEAL_3);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void update() {
        Meal expected = new Meal(MEAL_1);
        expected.setCalories(CALORIES_2);
        expected.setDescription(DESCRIPTION_2);
        service.update(expected, USER_ID);
        assertMatch(service.get(expected.getId(), USER_ID), expected);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlienMeal() {
        Meal expected = new Meal(MEAL_1);
        service.update(expected, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal expected = new Meal(DATE_TIME_1, DESCRIPTION_1, CALORIES_1);
        Meal actual = service.create(expected, USER_ID);
        expected.setId(actual.getId());
        assertMatch(actual, expected);
    }
}
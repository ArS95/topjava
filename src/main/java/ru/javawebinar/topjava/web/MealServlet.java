package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.TestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealsMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealsMapStorage();
        TestData.TEST_DATA_LIST.forEach(uM -> storage.add(uM));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(localDateTime, description, calories);
        String action = request.getParameter("action");
        if (action.equals("edit")) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            storage.update(meal);
        } else {
            storage.add(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredByCycle(storage.getAll(), LocalTime.of(7, 0), LocalTime.of(12, 0)));
            request.getRequestDispatcher("/jsp/meals.jsp").forward(request, response);
            return;
        }

        String strId = request.getParameter("id");
        int id = Integer.parseInt(strId != null ? strId : "-1");
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            case "add":
                meal = new Meal();
                break;
            case "edit":
                meal = storage.get(id);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meals", meal);
        request.getRequestDispatcher("/jsp/edit.jsp").forward(request, response);
    }
}

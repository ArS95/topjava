package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(JspMealController.PATH)
public class JspMealController extends AbstractMealController{
    public static final String PATH = "/meals";

    @Autowired
    private MealService service;

    @GetMapping("/delete")
    public String delete(int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String forCreate(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest request) throws IOException{
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        service.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String forUpdate(Model model, int id) {
        Meal meal = service.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request, int id) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        service.update(meal, id);
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        int userId = SecurityUtil.authUserId();

        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}

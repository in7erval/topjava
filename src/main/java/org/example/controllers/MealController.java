package org.example.controllers;

import org.example.dao.MealDAO;
import org.example.dao.UserDAO;
import org.example.models.Meal;
import org.example.models.User;
import org.example.to.MealTo;
import org.example.util.FilterObject;
import org.example.util.UserMealsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class MealController {
    private final MealDAO mealDAO;
    private static final int DEFAULT_CALORIES_PER_DAY = 2000;
    private final FilterObject filterObject;
    private final MainController mainController;

    @Autowired
    public MealController(MealDAO mealDAO, FilterObject filterObject, MainController mainController) {
        this.mealDAO = mealDAO;
        this.filterObject = filterObject;
        this.mainController = mainController;
    }

    @GetMapping()
    public String index(Model model) {
        if (!registeredUser().isAdmin())
            filterObject.setIndexAll(false);
        LocalTime fromTime = filterObject.getFromTime();
        LocalTime toTime = filterObject.getToTime();
        LocalDate fromDate = filterObject.getFromDate();
        LocalDate toDate = filterObject.getToDate();
        List<Meal> all;
        List<MealTo> filteredTo;
        if (filterObject.isIndexAll() && registeredUser().isAdmin()) {
            all = mealDAO.getAllBetweenDate(fromDate, toDate);
            filteredTo = UserMealsUtil.filteredAllByStreams(all, fromTime, toTime, DEFAULT_CALORIES_PER_DAY);
        }
        else {
            all = mealDAO.getAllByUserIdBetweenDate(mainController.getCurrentUser().getId(), fromDate, toDate);
            filteredTo = UserMealsUtil.filteredByStreams(all, fromTime, toTime, DEFAULT_CALORIES_PER_DAY);
        }
        filteredTo.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));

        model.addAttribute("meals", filteredTo);
        return "meals/index";
    }

    @GetMapping("/all")
    public String indexAll() {
        filterObject.setIndexAll(true);

        return "redirect:/meals";
    }

    @GetMapping("/all/reset")
    public String resetIndexAll() {
        filterObject.setIndexAll(false);

        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("filterObject") FilterObject filterObject) {
        filterObject.setToTime(filterObject.getToTime() == null ? LocalTime.MAX : filterObject.getToTime());
        filterObject.setToDate(filterObject.getToDate() == null ? LocalDate.MAX : filterObject.getToDate());
        filterObject.setFromTime(filterObject.getFromTime() == null ? LocalTime.MIN : filterObject.getFromTime());
        filterObject.setFromDate(filterObject.getFromDate() == null ? LocalDate.MIN : filterObject.getFromDate());
        return "redirect:/meals";
    }

    @GetMapping("/filter/reset")
    public String filterReset() {
        filterObject.setFromDate(LocalDate.MIN);
        filterObject.setFromTime(LocalTime.MIN);
        filterObject.setToDate(LocalDate.MAX);
        filterObject.setToTime(LocalTime.MAX);
        return "redirect:/meals";
    }


    @GetMapping("/add")
    public String newPerson(@ModelAttribute("meal") Meal meal, Model model) {
        model.addAttribute("action", "add");
        model.addAttribute("method", "POST");
        model.addAttribute("title", "Добавить новую запись");
        model.addAttribute("value", "Добавить");
        return "meals/form";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("meal") Meal meal) {
        meal.setUserId(mainController.getCurrentUser().getId());
        mealDAO.save(meal);
        return "redirect:/meals";
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute("meal") Meal meal) {
        mealDAO.update(meal.getId(), meal);
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        mealDAO.delete(id);
        return "redirect:/meals";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("meal", mealDAO.getById(id));
        model.addAttribute("action", "edit");
        model.addAttribute("method", "PATCH");
        model.addAttribute("title", "Изменить запись");
        model.addAttribute("value", "Изменить");
        return "meals/form";
    }

    @ModelAttribute("formatter")
    public DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @ModelAttribute("filterObject")
    public FilterObject filterObject() {
        return filterObject;
    }

    @ModelAttribute("registeredUser")
    public User registeredUser() {
        return mainController.getCurrentUser();
    }

    @ModelAttribute("userDAO")
    public UserDAO userDAO() {
        if (mainController.getCurrentUser().isAdmin())
            return mainController.getUserDAO();
        else
            return null;
    }



}

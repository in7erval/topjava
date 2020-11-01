package org.example.dao;

import org.example.models.Meal;
import org.example.to.MealTo;
import org.example.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class MealDAO {
    private static AtomicInteger MEAL_COUNT = new AtomicInteger(0);
    private List<Meal> meals;

    {
        meals = new ArrayList<>();

        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 2));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 3));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 2));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 3));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500,2));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000,3));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500,1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100,2));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000,3));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500,1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410,2));
    }

    public List<Meal> getAllByUserId(Integer userId) {
        return meals.stream().filter(meal -> meal.getUserId() == userId).collect(Collectors.toList());
    }

    public List<Meal> getAllByUserIdBetweenDate(Integer userId, LocalDate from, LocalDate to) {
        return meals
                .stream()
                .filter(meal -> meal.getUserId() == userId
                        && TimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), from, to))
                .collect(Collectors.toList());
    }

    public List<Meal> getAllBetweenDate(LocalDate from, LocalDate to) {
        return meals
                .stream()
                .filter(meal -> TimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), from, to))
                .collect(Collectors.toList());
    }

    public List<Meal> getAll() {
        return meals;
    }

    public Meal getById(int id) {
        return meals.stream().filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    public void save(Meal meal) {
        meal.setId(MEAL_COUNT.incrementAndGet());
        meals.add(meal);
    }

    public void update(int id, Meal updatedMeal) {
        Meal mealToBeUpdated = getById(id);

        mealToBeUpdated.setDescription(updatedMeal.getDescription());
        mealToBeUpdated.setCalories(updatedMeal.getCalories());
        mealToBeUpdated.setDateTime(updatedMeal.getDateTime());
    }

    public synchronized void delete(int id) {
        meals.removeIf(x -> x.getId() == id);
    }

}

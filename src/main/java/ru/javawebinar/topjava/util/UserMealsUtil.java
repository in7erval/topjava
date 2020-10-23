package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
	public static void main(String[] args) {
		List<UserMeal> meals = Arrays.asList(
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
				new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
		);

		List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
	}

	public static List<MealTo> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		List<MealTo> filteredUserMealsWithExcesses = new ArrayList<>();
		Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();

		meals.forEach(meal -> sumCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));
		meals.forEach(meal -> {
		    LocalTime localTime = meal.getDateTime().toLocalTime();

		    if (TimeUtil.isBetweenHalfOpen(localTime, startTime, endTime)) {
                boolean excess = sumCaloriesPerDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay;
                MealTo userMealWithExcess = new MealTo(meal.getDateTime(),
                                                                        meal.getDescription(),meal.getCalories(), excess);

                filteredUserMealsWithExcesses.add(userMealWithExcess);
            }
        });
		return filteredUserMealsWithExcesses;
	}

	public static List<MealTo> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Function<UserMeal, LocalDateTime> getDateTime = UserMeal::getDateTime;
		Map<LocalDate, IntSummaryStatistics> sumCaloriesPerDay = meals
														.stream()
														.collect(
															Collectors.groupingBy(getDateTime.andThen(LocalDateTime::toLocalDate),
															Collectors.summarizingInt(UserMeal::getCalories))
														);
		return meals
					.stream()
					.filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
					.map(x -> new MealTo(x.getDateTime(), x.getDescription(), x.getCalories(), sumCaloriesPerDay.get(x.getDateTime().toLocalDate()).getSum() > caloriesPerDay))
					.collect(Collectors.toList());
	}
}

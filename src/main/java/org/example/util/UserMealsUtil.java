package org.example.util;

import org.example.models.Meal;
import org.example.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
	public static void main(String[] args) {
		List<Meal> meals = Arrays.asList(
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
		);

		List<Meal> mealsWithId = Arrays.asList(
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 2),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 3),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 1),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 2),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 3),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 1),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 2),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 3),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500,1),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 2),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 3),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500,1),
				new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410,2)
		);

		//List<MealTo> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
		//mealsTo.forEach(System.out::println);

		List<MealTo> mealsTo = filteredAllByStreams(mealsWithId, LocalTime.MIN, LocalTime.MAX, 2000);
		mealsTo.forEach(System.out::println);
        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
	}

	public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		List<MealTo> filteredUserMealsWithExcesses = new ArrayList<>();
		Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();

		meals.forEach(meal -> sumCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));
		meals.forEach(meal -> {
		    LocalTime localTime = meal.getDateTime().toLocalTime();

		    if (TimeUtil.isBetweenHalfOpen(localTime, startTime, endTime)) {
                boolean excess = sumCaloriesPerDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay;
                MealTo userMealWithExcess = new MealTo(meal.getId(), meal.getUserId(), meal.getDateTime(),
                                                                        meal.getDescription(),meal.getCalories(), excess);

                filteredUserMealsWithExcesses.add(userMealWithExcess);
            }
        });
		return filteredUserMealsWithExcesses;
	}

	public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Function<Meal, LocalDateTime> getDateTime = Meal::getDateTime;
		Map<LocalDate, IntSummaryStatistics> sumCaloriesPerDay = meals
														.stream()
														.collect(
															Collectors.groupingBy(getDateTime.andThen(LocalDateTime::toLocalDate),
															Collectors.summarizingInt(Meal::getCalories))
														);
		return meals
					.stream()
					.filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
					.map(x -> new MealTo(x.getId(), x.getUserId(), x.getDateTime(), x.getDescription(), x.getCalories(), sumCaloriesPerDay.get(x.getDateTime().toLocalDate()).getSum() > caloriesPerDay))
					.collect(Collectors.toList());
	}

	public static List<MealTo> filteredAllByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
		Map<Integer, List<MealTo>> userIdToMealsTo = meals
				.stream()
				.collect(Collectors.toMap(
						Meal::getUserId,
						x -> {
							List<Meal> inner = new ArrayList<>();
							inner.add(x);
							return filteredByStreams(inner, startTime, endTime, caloriesPerDay);
						},
						(left, right) -> {
							left.addAll(right);
							return left;
						},
						HashMap::new)
				);
		List<MealTo> mealTos = new ArrayList<>();
		for (List<MealTo> mealTo : userIdToMealsTo.values())
			mealTos.addAll(mealTo);
		return mealTos;
	}


}

package org.example.to;

import java.time.LocalDateTime;

public class MealTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    private final int userId;

    public static final int EXCESS_VALUE = 2000;

    public MealTo(Integer id, int userId, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getCalories() {
        return calories;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {return id;}

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}

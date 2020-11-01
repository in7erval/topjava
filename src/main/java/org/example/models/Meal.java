package org.example.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class Meal extends AbstractBaseEntity {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;
    private String description;
    private int calories;
    private int userId;

    public Meal() {
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, int userId) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
        this.userId = userId;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
    }

    public Meal(LocalDateTime dateTime, String description, int calories, int userId) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = null;
        this.userId = userId;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = null;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}

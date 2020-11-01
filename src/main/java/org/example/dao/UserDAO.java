package org.example.dao;

import org.example.models.Meal;
import org.example.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserDAO {
    private static AtomicInteger USER_COUNT = new AtomicInteger(0);
    private List<User> users;

    {
        users = new ArrayList<>();

        save(new User("admin", "admin@mail", "admin", true));
        save(new User("user", "user@mail", "user", false));
        save(new User("user1", "user1@mail", "user", false));
    }

    public synchronized List<User> getAll() {
        return users;
    }

    public synchronized User getById(int id) {
        return users.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public synchronized void save(User user) {
        user.setId(USER_COUNT.incrementAndGet());
        users.add(user);
    }

    public synchronized void update(int id, User updatedUser) {
        User userToBeUpdated = getById(id);
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setRegisterDate(updatedUser.getRegisterDate());
        userToBeUpdated.setPassword(updatedUser.getPassword());
    }

    public synchronized void delete(int id) {
        users.removeIf(x -> x.getId() == id);
    }
}

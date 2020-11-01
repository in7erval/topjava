package org.example.controllers;

import org.example.dao.UserDAO;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserDAO userDAO;
    private User currentUser;

    @Autowired
    public MainController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String register(Model model) {
        model.addAttribute("users", userDAO.getAll());
        model.addAttribute("userForm", new User());
        return "register";
    }

    @PostMapping
    public String selectedUser(@ModelAttribute("userForm") User user) {
        currentUser = userDAO.getById(user.getId());
        return "redirect:/meals";
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}

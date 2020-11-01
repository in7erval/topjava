package org.example.controllers;

import org.example.dao.UserDAO;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDAO userDAO;
    private final MainController mainController;

    @Autowired
    public UserController(UserDAO userDAO, MainController mainController) {
        this.userDAO = userDAO;
        this.mainController = mainController;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", mainController.getCurrentUser());
        return "user/profile";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userDAO.update(user.getId(), user);
        return "user/profile";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userDAO.getAll());
        return "user/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userDAO.delete(id);
        return "redirect:/user/list";
    }

}

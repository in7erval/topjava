package ru.javawebinar.topjava.servlets;

import ru.javawebinar.topjava.model.Model;
import ru.javawebinar.topjava.model.UserMeal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class AddMealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("addMeal.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description").trim();
        String caloriesStr = req.getParameter("calories").trim();
        String localDateTimeStr = req.getParameter("datetime").trim();
        if (!description.isEmpty() && !caloriesStr.isEmpty() && !localDateTimeStr.isEmpty()) {
            int calories = Integer.parseInt(req.getParameter("calories"));
            LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr);
            UserMeal meal = new UserMeal(localDateTime, description, calories);
            Model model = Model.getInstance();
            model.add(meal);
            req.setAttribute("description", description);
        }
        doGet(req, resp);
    }
}

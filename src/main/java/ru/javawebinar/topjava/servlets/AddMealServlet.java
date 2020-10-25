package ru.javawebinar.topjava.servlets;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddMealServlet extends HttpServlet {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final MealDB db = MealDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("addMeal.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description").trim();
        String caloriesStr = req.getParameter("calories");
        int calories = (caloriesStr != null) ? Integer.parseInt(caloriesStr) : 0;
        String localDateTimeStr = req.getParameter("datetime").trim();
        String id = req.getParameter("id");
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr, formatter);

        Meal meal = new Meal(id == null || id.isEmpty() ? null : Integer.parseInt(id), localDateTime, description, calories);
        db.save(meal);
        req.setAttribute("description", description);
        if (id == null || id.isEmpty())
            doGet(req, resp);
        else
            resp.sendRedirect("meals");
    }
}

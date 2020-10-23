package ru.javawebinar.topjava.servlets;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.Model;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Model model = Model.getInstance();
        req.setAttribute("mealsList", UserMealsUtil.filteredByStreams(model.list(), LocalTime.MIN, LocalTime.MAX, MealTo.EXCESS_VALUE));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("meals.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

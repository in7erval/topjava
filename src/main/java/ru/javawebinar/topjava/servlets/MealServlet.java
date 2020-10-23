package ru.javawebinar.topjava.servlets;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealDB;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MealDB db = MealDB.getInstance();
        String action = req.getParameter("action");
        String idStr = req.getParameter("id");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(idStr);
            db.delete(id);
            resp.sendRedirect("meals");
        } else {
            req.setAttribute("mealsList", UserMealsUtil.filteredByStreams(new ArrayList<>(db.getAll()), LocalTime.MIN, LocalTime.MAX, MealTo.EXCESS_VALUE));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("meals.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}

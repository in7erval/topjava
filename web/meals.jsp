<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список записей</title>
    <meta charset="UTF-8">
</head>
<body>
<h2><a href="index.jsp">К началу</a></h2>
<hr/>
<div>
    <h1>Список записей</h1>
</div>
<p><a href="/topjava/addMeal" style="padding: 2px; margin-bottom: 5px;">Добавить новую запись</a></p>
<div>
    <%
        List<MealTo> meals = (List<MealTo>) request.getAttribute("mealsList");
        if (meals != null && !meals.isEmpty()) {
            out.println("<div><table border=\"1\" cellpadding=\"10\"><tr><td>Дата</td><td>Описание</td><td>Калории</td></tr>");
            for (MealTo meal : meals) {
                String time = meal.getDateTime().toLocalDate() + " " + meal.getDateTime().toLocalTime().getHour() + ":" + meal.getDateTime().toLocalTime().getMinute();
                out.println("<tr style=\"color:" + (meal.isExcess() ? "red" : "green") + ";\">");
                out.println("<td>" + time + "</td>");
                out.println("<td>" + meal.getDescription() + "</td>");
                out.println("<td>" + meal.getCalories() + "</td></tr>");
            }
            out.println("</table></div>");
        } else out.println("<div><h2>Записей о еде ещё нет!</h2></div>");
    %>
</div>

</body>
</html>

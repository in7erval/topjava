<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
<p><a href="addMeal" style="padding: 2px; margin-bottom: 5px;">Добавить новую запись</a></p>
<div>
    <%
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<MealTo> meals = (List<MealTo>) request.getAttribute("mealsList");
        if (meals != null && !meals.isEmpty()) {
            out.println("<div><table border=\"1\" cellpadding=\"10\"><tr style=\"text-align:center;\"><td><b>Дата</b></td><td><b>Описание</b></td><td><b>Калории</b></td><td></td><td></td></tr>");
            for (MealTo meal : meals) {
                out.println("<tr style=\"color:" + (meal.isExcess() ? "red" : "green") + ";\">");
                out.println("<td>" + meal.getDateTime().format(formatter) + "</td>");
                out.println("<td>" + meal.getDescription() + "</td>");
                out.println("<td>" + meal.getCalories() + "</td>");
                out.println("<td><a href=\"meals?action=delete&id=" + meal.getId() +"\">delete</a></td>");
                out.println("<td><a href=\"meals?action=update&id=" + meal.getId() +"\">update</a></td>");
                out.println("</tr>");
            }
            out.println("</table></div>");
        } else out.println("<div><h2>Записей о еде ещё нет!</h2></div>");
    %>
</div>

</body>
</html>

<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление записи</title>
    <meta charset="UTF-8">
</head>
<body>
<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    Meal meal = (Meal) request.getAttribute("meal");
    String action = "Добавить";
    String dateTime = null;
    String description = null;
    Integer calories = null;
    Integer id = null;
    if (meal != null) {
        action = "Обновить";
        dateTime = meal.getDateTime().format(formatter);
        description = meal.getDescription();
        calories = meal.getCalories();
        id = meal.getId();
    }
%>
<div>
    <h2><a href='index.jsp'>К началу</a></h2>
    <hr>
    <div>
        <h1><%out.print(action);%> запись о еде</h1>
    </div>
    <form method="post" action="addMeal">
        <fieldset style="width: 500px;">
            <legend>Информация о еде</legend>
            <label for="datetime">Дата и время:
                <input required style="margin-left: 50px; margin-bottom: 10px;" type="datetime-local" name="datetime" id="datetime" value="<%out.print(dateTime == null ? LocalDateTime.now().format(formatter) : dateTime);%>"> (формат: 2020-10-10T18:18)<br/>
            </label>
            <label for="description">Описание:
                <input required style="margin-left: 72px; margin-bottom: 10px;" type="text" name="description" id="description" size="30" value="<%out.print(description == null ? "" : description);%>"><br/>
            </label>
            <label for="calories">Кол-во калорий:
                <input min="0" required style="margin-left: 30px; margin-bottom: 10px;" type="number" name="calories" id="calories" value="<%out.print(calories == null ? "" : calories);%>"><br/>
            </label>
            <input type="hidden" name="id" value="<%out.print(id == null ? "" : id);%>">
        </fieldset>
        <input type="submit" value="<%out.print(action);%>" style="width: 100px;">
    </form>
    <%
        if (request.getAttribute("description") != null) {
            out.println("<div><h5>Запись '" + request.getAttribute("description") + "' добавлена!</h5></div>");
        }
    %>
</div>
</body>
</html>

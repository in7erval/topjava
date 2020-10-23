<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление записи</title>
    <meta charset="UTF-8">
</head>
<body>
<div>
    <h2><a href='index.jsp'>К началу</a></h2>
    <hr>
    <div>
        <h1>Добавить запись о еде</h1>
    </div>
    <form method="post">
        <fieldset style="width: 500px;">
            <legend>Информация о еде</legend>
            <label for="datetime">Дата и время:
                <input style="margin-left: 50px; margin-bottom: 10px;" type="datetime-local" name="datetime" id="datetime"> (формат: 2020-10-10T18:18)<br/>
            </label>
            <label for="description">Описание:
                <input style="margin-left: 72px; margin-bottom: 10px;" type="text" name="description" id="description" size="30"><br/>
            </label>
            <label for="calories">Кол-во калорий:
                <input style="margin-left: 30px; margin-bottom: 10px;" type="text" name="calories" id="calories"><br/>
            </label>
        </fieldset>
        <input type="submit" value="Добавить" style="width: 100px;">
    </form>
    <%
        if (request.getAttribute("description") != null) {
            out.println("<div><h5>Запись '" + request.getAttribute("description") + "' добавлена!</h5></div>");
        }
    %>
</div>
</body>
</html>

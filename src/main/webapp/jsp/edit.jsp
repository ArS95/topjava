<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>${meal.description == null ? 'Добавление':'Редактирование'} еды</title>
</head>
<body>
<form id="meal" method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.description == null ? '-1':meal.id}">
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <td>Дата</td>
            <td><input type="datetime-local" name="date" value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td>Описание</td>
            <td><input type="text" name="description" value="${meal.description}"></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="text" name="calories" value="${meal.calories}"></td>
        </tr>
    </table>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()">Отменить</button>
</form>
</body>
</html>

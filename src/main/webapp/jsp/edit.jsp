<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>${meal.description == null ? 'Добавление':'Редактирование'} еды</title>
</head>
<body>
<form id="meal" method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.description == null ? '-1':meal.id}">
    <table class="table" border="1" cellpadding="8" cellspacing="0">
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
    <button type="submit" class="btn btn-outline-success">Сохранить</button>
    <button onclick="window.history.back()" class="btn btn-outline-danger">Отменить</button>
</form>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>

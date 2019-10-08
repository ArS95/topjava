<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>
</head>
<body>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Изменить</th>
            <th>Удалить</th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:set var="color" value="${meal.excess ? 'red':'green'}"/>
            <tr style="color: ${color}">
                <td>${DateUtil.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=edit"><img src="jsp/img/pencil.png"></a></td>
                <td><a href="meals?id=${meal.id}&action=delete"><img src="jsp/img/delete.png"></a></td>
            </tr>
        </c:forEach>
    </table>
    <button type="button"><a href="?action=add">Добавить еду</a></button>
</section>
</body>
</html>

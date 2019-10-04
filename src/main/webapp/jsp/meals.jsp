<%--
  Created by IntelliJ IDEA.
  User: Darina
  Date: 04.10.2019
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
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
            <%--            <c:if test="${meal.excess == true}">--%>
            <tr style="color:  red" >
                <td>${meal.dateTime}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meal?id=${meal.id}&action=delete"><img src="delete.png"></a></td>
                <td><a href="meal?id=${meal.id}&action=edit"><img src="pencil.png"></a></td>
            </tr>
            <%--            </c:if>--%>
            <%--            <tr>--%>
            <%--                <td>${meal.dateTime}</td>--%>
            <%--                <td>${meal.description}</td>--%>
            <%--                <td>${meal.calories}</td>--%>
            <%--                <td><a href="meal?id=${meal.id}&action=delete"><img src="delete.png"></a></td>--%>
            <%--                <td><a href="meal?id=${meal.id}&action=edit"><img src="pencil.png"></a></td>--%>
            <%--            </tr>--%>
        </c:forEach>
    </table>
    <button type="button"><a href="?action=add">Добавить Резюме</a></button>
</section>
</body>
</html>

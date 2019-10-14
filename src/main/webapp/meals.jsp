<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Meal list</title>

    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <div class="jumbotron pt-10">
        <h3><a href="index.html">Home</a></h3>
        <hr/>
        <h2 class="text-center">My meal</h2>
        <div class="container">
            <div class="card border-light">
                <div class="card-body pb-0">
                    <form id="filter" method="post">
                        <div class="row">
                            <div class="offset-1 col-2">
                                <label>От даты</label>
                                <input class="form-control" type="date" name="startDate">
                            </div>
                            <div class="col-2">
                                <label>До даты</label>
                                <input class="form-control" type="date" name="endDate">
                            </div>
                            <div class="offset-2 col-2">
                                <label>От времени</label>
                                <input class="form-control" type="time" name="startTime">
                            </div>
                            <div class="col-2">
                                <label>До времени</label>
                                <input class="form-control" type="time" name="endTime">
                            </div>
                        </div>
                        <hr>
                        <div class="ard-footer text-right">
                            <button class="btn btn-danger" type="button" onclick="window.history.back()">Отменить</button>
                            <button class="btn btn-primary" type="submit"> Отфильровать</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <button class="btn btn-outline-primary" type="button"><a href="meals?action=create">Add Meal</a></button>
        <br><br>
        <table class="table table-striped" border="1" cellpadding="8" cellspacing="0">
            <thead class="thead-dark">
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr class="${meal.excess ? 'excess' : 'normal'}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</section>
</body>
</html>
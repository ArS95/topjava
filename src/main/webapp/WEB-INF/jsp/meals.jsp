<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/mealsHeadTag.jsp"/>
<body>
<section>
    <c:set var="path">${pageContext.request.contextPath}</c:set>
    <h3><a href="${path}/"><spring:message code="app.home"/></a></h3>
    <hr/>
    <h2><spring:message code="meal.meals"/></h2>
    <form method="post" action="${path}/meals/filter">
        <dl>
            <dt>
                <spring:message code="filter.fromDate"/>:
            </dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>
                <spring:message code="filter.toDate"/>:
            </dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>
                <spring:message code="filter.fromTime"/>:
            </dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>
                <spring:message code="filter.toTime"/>:
            </dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="filter.filter"/></button>
    </form>
    <hr/>
    <a href="${path}/meals/create"><spring:message code="common.add"/></a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="${path}/meals/update?id=${meal.id}"><spring:message code="meal.edit"/></a></td>
                <td><a href="${path}/meals/delete?id=${meal.id}"><spring:message code="meal.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
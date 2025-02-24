<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Actor</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2><fmt:message key="page.actor.info"/>:</h2>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px"><fmt:message key="page.name"/>:</div>
        <div>${requestScope.actor.name}</div>
    </div>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px"><fmt:message key="page.birthday"/>:</div>
        <div>${requestScope.actor.dateOfBirthday}</div>
    </div>
    <br>
    <div>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <form action="${requestScope.request.contextPath}/admin/add-actor" method="get" style="display: inline;">
                <input type="hidden" name="actorId" value="${requestScope.actor.id}">
                <button type="submit"><fmt:message key="update"/></button>
            </form>
            <form action="${requestScope.request.contextPath}/admin/delete-actor" method="post"
                  style="display: inline;">
                <input type="hidden" name="actorId" value="${requestScope.actor.id}">
                <button type="submit"><fmt:message key="delete"/></button>
            </form>
        </c:if>
    </div>
    <div>
        <h3><fmt:message key="list.movies"/>:</h3>
        <div>
            <c:forEach var="movie" items="${requestScope.actor.movies}">
                <li>
                    <a href="${pageContext.request.contextPath}/movie?movieId=${movie.id}">${movie.description}</a>
                </li>
            </c:forEach>
        </div>
    </div>
    <br>
    <br>
    <div>
        <form action="${requestScope.request.contextPath}/movies" method="get">
            <button type="submit"><fmt:message key="page.main-page"/></button>
        </form>
    </div>
</div>
</body>
</html>

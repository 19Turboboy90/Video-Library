<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список актеров</title>
</head>
<%@ include file="header.jsp" %>
<body>
<div>
    <h1>Список актеров:</h1>
    <c:forEach var="actor" items="${requestScope.actors}">
        <li>
            <a href="${pageContext.request.contextPath}/actor?actorId=${actor.id}">${actor.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="admin" method="get">
                    <button type="submit">Добавить актера</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

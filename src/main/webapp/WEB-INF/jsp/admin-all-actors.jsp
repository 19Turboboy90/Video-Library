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
    <div>
        <form action="${pageContext.request.contextPath}/admin/actors-page" method="get">
            <label for="prefix">Поиск актера</label>
            <input type="text" id="prefix" name="prefix" placeholder="ввести имя актера">
            <button type="submit">Отфильтровать</button>
            <button type="submit">Отмена</button>
        </form>
    </div>
    <c:forEach var="actor" items="${requestScope.actors}">
        <li>
            <a href="${pageContext.request.contextPath}/actor?actorId=${actor.id}">${actor.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="${pageContext.request.contextPath}/admin/add-actor" method="get">
                    <button type="submit">Добавить актера</button>
                </form>
            </div>
        </div>
        <br>
        <br>
        <div>
            <form action="${requestScope.request.contextPath}/admin" method="get">
                <button type="submit">На главную страницу</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>

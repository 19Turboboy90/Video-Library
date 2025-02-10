<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1>Список пользователей:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/users-page" method="get">
            <label for="prefix">Поиск пользователя</label>
            <input type="text" id="prefix" name="prefix" placeholder="ввести имя пользователя">
            <button type="submit">Отфильтровать</button>
            <button type="submit">Отмена</button>
        </form>
    </div>
    <c:forEach var="user" items="${requestScope.users}">
        <li>
            <a href="${pageContext.request.contextPath}/user?userId=${user.id}">${user.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
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

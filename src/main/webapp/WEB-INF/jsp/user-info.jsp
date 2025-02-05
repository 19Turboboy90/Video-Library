<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пользователь</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>Информация об пользователе</h2>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Имя:</div>
        <div>${requestScope.user.name}</div>
    </div>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Почта:</div>
        <div>${requestScope.user.email}</div>
    </div>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Роль:</div>
        <div>${requestScope.user.role}</div>
    </div>
    <br>
    <div>
        <form action="${requestScope.request.contextPath}/admin/update-user" method="get" style="display: inline;">
            <input type="hidden" name="userId" value="${requestScope.user.id}">
            <button type="submit">Изменить пользователя</button>
        </form>
        <form action="${requestScope.request.contextPath}/admin/user-delete" method="post"
              style="display: inline;">
            <input type="hidden" name="userId" value="${requestScope.user.id}">
            <button type="submit">Удалить пользователя</button>
        </form>
    </div>
    <br>
    <br>
    <div>
        <form action="${requestScope.request.contextPath}/movies" method="get">
            <button type="submit">На главную страницу</button>
        </form>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>Регистрация пользователя</h2>
    <form action="${requestScope.request.contextPath}/registration" method="post">
        <div>
            <label for="name">
                <input type="text" name="name" id="name" placeholder="Введите имя" required>
            </label>
        </div>
        <br>
        <div>
            <label for="email">
                <input type="text" name="email" id="email" placeholder="Введите почту" required>
            </label>
        </div>
        <br>
        <div>
            <label for="password">
                <input type="password" name="password" id="password" placeholder="Введите пароль" required>
            </label>
        </div>
        <br>
        <br>
        <button type="submit">Сохранить</button>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <div>${error.message}</div>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>

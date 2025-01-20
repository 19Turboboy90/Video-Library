<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label for="email">
                <input type="text" name="email" id="email" value="${param.email}"
                       placeholder="Введите почту" required>
            </label>
        </div>
        <br>
        <div>
            <label for="password">
                <input type="password" name="password" id="password" value="${param.password}"
                       placeholder="Введите пароль" required>
            </label>
        </div>
        <br>
        <div>
            <button type="submit">Войти</button>
        </div>
        <br>
        <a href="${pageContext.request.contextPath}/registration">
            <button type="button">Зарегистрироваться</button>
        </a>
        <c:if test="${param.error != null}">
            <div style="color: red">
                <span>почта или пароль не корректны</span>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>

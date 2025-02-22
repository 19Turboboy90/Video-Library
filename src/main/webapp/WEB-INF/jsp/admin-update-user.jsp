<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>Изменение пользователя</h2>
    <form action="${requestScope.request.contextPath}/admin/update-user" method="post">
        <div>
            <input type="hidden" name="userId" value="${requestScope.user != null ? requestScope.user.id : ''}">
        </div>
        <div>
            <label for="name">
                <input type="text" name="name" id="name"
                       value="${requestScope.user != null ? requestScope.user.name : ''}" placeholder="Введите имя"
                       required>
            </label>
        </div>
        <br>
        <div>
            <label for="email">
                <input type="text" name="email" id="email"
                       value="${requestScope.user != null ? requestScope.user.email : ''}" placeholder="Введите почту"
                       required>
            </label>
        </div>
        <br>
        <div>
            <label for="password">
                <input type="password" name="password" id="password"
                       value="${requestScope.user != null ? requestScope.user.password : ''}"
                       placeholder="Введите пароль"
                       required>
            </label>
        </div>
        <br>
        <br>
        <div>
            <label for="role">
                <select name="role" id="role">
                    <c:forEach items="${requestScope.roles}" var="role">
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <br>
        <br>
        <button type="submit">Сохранить</button>
        <br>
        <br>


        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <div>${error.message}</div>
                </c:forEach>
            </div>
        </c:if>
    </form>

    <div>
        <form action="${requestScope.request.contextPath}/admin" method="get">
            <button type="submit">На главную страницу</button>
        </form>
    </div>
</div>
</body>
</html>

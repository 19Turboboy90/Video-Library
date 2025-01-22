<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>save-director</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>Сохранить режиссера:</h2>
    <form action="${requestScope.request.contextPath}/admin/add-director" method="post">
        <div>
            <label for="name">
                <input type="text" name="name" id="name" placeholder="Введите имя" required>
            </label>
        </div>
        <br>
        <div>
            <label for="birthday">Введите дату рождения:
                <input type="date" name="birthday" id="birthday" placeholder="Введите дату рождения" required>
            </label>
        </div>
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

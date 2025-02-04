<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>save-director</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>${requestScope.director != null && requestScope.director.id != null ? "Обновить режиссера:" : "Сохранить режиссера:"}</h2>
    <form action="${requestScope.request.contextPath}/admin/add-director" method="post">
        <div>
            <input type="hidden" name="directorId"
                   value="${requestScope.director != null ? requestScope.director.id : ''}"/>
        </div>
        <div>
            <label for="name">
                <input type="text" name="name" id="name"
                       value="${requestScope.director != null ? requestScope.director.name : ''}"
                       placeholder="Введите имя" required>
            </label>
        </div>
        <br>
        <div>
            <label for="birthday">Введите дату рождения:
                <input type="date" name="birthday" id="birthday"
                       value="${requestScope.director != null ? requestScope.director.dateOfBirthday : ''}"
                       placeholder="Введите дату рождения" required>
            </label>
        </div>
        <br>
        <button
                type="submit">${requestScope.director != null && requestScope.director.id != null ? 'Редактировать' : 'Сохранить'}
        </button>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <div>${error.message}</div>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
<br>
<br>
<div>
    <form action="${requestScope.request.contextPath}/admin" method="get">
        <button type="submit">На главную страницу</button>
    </form>
</div>
</body>
</html>

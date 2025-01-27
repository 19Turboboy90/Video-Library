<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список режиссеров</title>
</head>
<body>
<div>
    <h1>Список режиссеров:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/directors-page" method="get">
            <label for="prefix">Поиск режиссера</label>
            <input type="text" id="prefix" name="prefix" placeholder="ввести имя режиссера">
            <button type="submit">Отфильтровать</button>
            <button type="submit">Отмена</button>
        </form>
    </div>
    <c:forEach var="director" items="${requestScope.directors}">
        <li>
            <a href="${pageContext.request.contextPath}/director?directorId=${director.id}">${director.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="${pageContext.request.contextPath}/admin/add-director" method="get">
                    <button type="submit">Добавить режиссера</button>
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

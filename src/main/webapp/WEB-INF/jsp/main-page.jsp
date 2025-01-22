<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список фильмов</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1>Список фильмов:</h1>
    <div>
        <form action="movies" method="get">
            <label for="fromDate">Поиск от</label>
            <input type="text" id="fromDate" name="fromDate" placeholder="ввести только год">
            <label for="toDate">Поиск до</label>
            <input type="text" id="toDate" name="toDate" placeholder="ввести только год">
            <button type="submit">Отфильтровать</button>
            <button type="submit">Отмена</button>
        </form>
    </div>

    <c:forEach var="movie" items="${requestScope.movies}">
        <li>
            <a href="${pageContext.request.contextPath}/movie?movieId=${movie.id}">${movie.description}</a>
        </li>
    </c:forEach>
    <br>
    <div style="display: flex">
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <div style="margin-right: 10px">
                <form action="admin/add-movie" method="get">
                    <button type="submit">Добавить фильм</button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/add-actor" method="get">
                    <button type="submit">Добавить актера</button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/add-director" method="get">
                    <button type="submit">Добавить режиссера</button>
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

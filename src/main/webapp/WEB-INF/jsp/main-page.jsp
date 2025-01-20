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
    <form action="movies" method="get">
        <label for="fromDate">Поиск от</label>
        <input type="text" id="fromDate" name="fromDate" placeholder="ввести только год">
        <label for="toDate">Поиск до</label>
        <input type="text" id="toDate" name="toDate" placeholder="ввести только год">
        <button type="submit">Отфильтровать</button>
        <form action="movies" method="get">
            <button type="submit">Отмена</button>
        </form>
    </form>

    <c:forEach var="movie" items="${requestScope.movies}">
        <li>
            <a href="${pageContext.request.contextPath}/movie?movieId=${movie.id}">${movie.description}</a>
        </li>
    </c:forEach>
</div>
</body>
</html>

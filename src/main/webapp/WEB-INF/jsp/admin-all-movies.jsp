<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%@ include file="header.jsp" %>
<body>
<div>
    <h1>Список фильмов:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/movies-page" method="get">
            <label for="prefix">Поиск фильма</label>
            <input type="text" id="prefix" name="prefix" placeholder="ввести название фильма">
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
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="${pageContext.request.contextPath}/admin/add-movie" method="get">
                    <button type="submit">Добавить фильм</button>
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

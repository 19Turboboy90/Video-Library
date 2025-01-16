<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Актер</title>
</head>
<body>
<div>
    <h2>Информация об режиссере</h2>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Имя:</div>
        <div>${requestScope.director.name}</div>
    </div>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Год рождения:</div>
        <div>${requestScope.director.dateOfBirthday}</div>
    </div>
    <div>
        <h3>Список фильмов</h3>
        <div>
            <c:forEach var="movie" items="${requestScope.director.movies}">
                <li>
                    <a href="${pageContext.request.contextPath}/movie?movieId=${movie.id}">${movie.description}</a>
                </li>
            </c:forEach>
        </div>
    </div>
    <br>
    <br>
    <div>
        <form action="movies" method="get">
            <button type="submit">На главную страницу</button>
        </form>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Актер</title>
</head>
<body>
<div>
    <h2>Информация об актере</h2>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Имя:</div>
        <div>${requestScope.actor.name}</div>
    </div>
    <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
        <div style="width: 160px">Год рождения:</div>
        <div>${requestScope.actor.dateOfBirthday}</div>
    </div>
    <div>
        <h3>Список фильмов</h3>
        <div>
            <c:forEach var="movie" items="${requestScope.actor.movies}">
                <li>
                    <a href="${pageContext.request.contextPath}/movie?movieId=${movie.id}">${movie.description}</a>
                </li>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>

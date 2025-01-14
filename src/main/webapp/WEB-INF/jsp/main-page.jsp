<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список фильмов</title>
</head>
<body>
<div>
    <h1>Список фильмов:</h1>
    <c:forEach var="movie" items="${requestScope.movies}">
        <li>
            <a href="${pageContext.request.contextPath}/movie?movieId=${movie.id}">${movie.description}</a>
        </li>
    </c:forEach>
</div>
</body>
</html>

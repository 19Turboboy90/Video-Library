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
        <%--        <form action="movies" method="get">--%>
        <%--            <label for="fromDate">Поиск от</label>--%>
        <%--            <input type="text" id="fromDate" name="fromDate" placeholder="ввести только год">--%>
        <%--            <label for="toDate">Поиск до</label>--%>
        <%--            <input type="text" id="toDate" name="toDate" placeholder="ввести только год">--%>
        <%--            <button type="submit">Отфильтровать</button>--%>
        <%--            <button type="submit">Отмена</button>--%>
        <%--        </form>--%>
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
                <form action="admin" method="get">
                    <button type="submit">Добавить фильм</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of movies</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1><fmt:message key="list.movies"/>:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/movies-page" method="get">
            <label for="prefix"><fmt:message key="page.search.movie"/></label>
            <input type="text" id="prefix" name="prefix" placeholder="<fmt:message key="page.search.movie.by.name"/>">
            <button type="submit"><fmt:message key="filter"/></button>
            <button type="submit"><fmt:message key="cancel"/></button>
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
                    <button type="submit"><fmt:message key="create"/></button>
                </form>
            </div>
        </div>
        <br>
        <br>
        <div>
            <form action="${requestScope.request.contextPath}/admin" method="get">
                <button type="submit"><fmt:message key="page.main-page"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>

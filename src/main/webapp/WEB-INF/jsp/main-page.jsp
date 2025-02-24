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
        <form action="movies" method="get">
            <label for="fromDate"><fmt:message key="page.search.movie.from"/></label>
            <input type="text" id="fromDate" name="fromDate"
                   placeholder="<fmt:message key="page.search.movie.by.year"/>">
            <label for="toDate"><fmt:message key="page.search.movie.to"/></label>
            <input type="text" id="toDate" name="toDate" placeholder="<fmt:message key="page.search.movie.by.year"/>">
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
    <div style="display: flex">
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <div style="margin-right: 10px">
                <form action="admin" method="get">
                    <button type="submit"><fmt:message key="admin.administration"/></button>
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

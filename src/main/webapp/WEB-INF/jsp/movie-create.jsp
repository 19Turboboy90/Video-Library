<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Save movie</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>
        <c:choose>
            <c:when test="${requestScope.movie != null && requestScope.movie.id != null}">
                <fmt:message key="update"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="save"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <form action="${requestScope.request.contextPath}/admin/add-movie" method="post">
        <div>
            <input type="hidden" name="movieId" value="${requestScope.movie != null ? requestScope.movie.id : ''}">
        </div>
        <div>
            <label for="name"><fmt:message key="page.search.movie.by.name"/>:
                <input type="text" name="name" id="name"
                       value="${requestScope.movie != null ? requestScope.movie.name : ''}"
                       placeholder="<fmt:message key="page.search.movie.by.name"/>">
            </label>
        </div>
        <br>
        <div>
            <label for="premiere_date"><fmt:message key="page.info.year"/>:
                <input type="date" name="premiere_date" id="premiere_date"
                       value="${requestScope.movie != null ? requestScope.movie.premierDate : ''}" required>
            </label>
        </div>
        <br>
        <div>
            <label for="country"><fmt:message key="page.info.country"/>:
                <input type="text" name="country" id="country"
                       value="${requestScope.movie != null ? requestScope.movie.country : ''}"
                       placeholder="<fmt:message key="page.info.country"/>">
            </label>
        </div>
        <br>
        <div>
            <label for="genre"><fmt:message key="page.info.genre"/>:
                <input type="text" name="genre" id="genre"
                       value="${requestScope.movie != null ? requestScope.movie.genre : ''}"
                       placeholder="<fmt:message key="page.info.genre"/>">
            </label>
        </div>
        <br>
        <div>
            <label for="director"><fmt:message key="page.info.director"/>:
                <select name="director" id="director" required>
                    <c:forEach items="${requestScope.directors}" var="director">
                        <option value="${director.id}">${director.name}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <br>
        <div>
            <label for="actor"><fmt:message key="page.info.actors"/>:
                <select name="actor" id="actor" multiple required>
                    <c:forEach items="${requestScope.actors}" var="actor">
                        <option value="${actor.id}">${actor.name}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <br>
        <c:choose>
            <c:when test="${requestScope.movie != null && requestScope.movie.id != null}">
                <c:set var="buttonLabel"><fmt:message key="update"/></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="buttonLabel"><fmt:message key="save"/></c:set>
            </c:otherwise>
        </c:choose>

        <button type="submit">${buttonLabel}</button>
    </form>
</div>
</body>
</html>

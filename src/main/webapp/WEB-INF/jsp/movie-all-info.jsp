<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="с" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Info about movie</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1>${requestScope.movie.name}</h1>
    <div style="display: flex">
        <div style="display: block; width: 50%">
            <h3><fmt:message key="page.info.movie"/></h3>
            <div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px"><fmt:message key="page.info.year"/>:</div>
                    <div>${requestScope.movie.premierDate}</div>
                </div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px"><fmt:message key="page.info.country"/>:</div>
                    <div>${requestScope.movie.country}</div>
                </div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px"><fmt:message key="page.info.genre"/>:</div>
                    <div>${requestScope.movie.genre}</div>
                </div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width:  160px"><fmt:message key="page.info.director"/>:</div>
                    <div>
                        <a href="${pageContext.request.contextPath}/director?directorId=${requestScope.movie.director.id}">${requestScope.movie.director.name}</a>
                    </div>
                </div>
                <br>
                <div>
                    <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">

                        <form action="${requestScope.request.contextPath}/admin/add-movie" method="get"
                              style="display: inline;">
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
                            <button type="submit"><fmt:message key="update"/></button>
                        </form>
                        <form action="${requestScope.request.contextPath}/admin/delete-movie" method="post"
                              style="display: inline;">
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
                            <button type="submit"><fmt:message key="delete"/></button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
        <div style="width: 50%">
            <h3><fmt:message key="page.info.actors"/>:</h3>
            <c:forEach var="actor" items="${requestScope.movie.actors}">
                <li>
                    <a href="${pageContext.request.contextPath}/actor?actorId=${actor.id}">${actor.name}</a>
                </li>
            </c:forEach>
        </div>
    </div>
    <br>
    <br>
    <div id="feedback">
        <с:forEach var="feed" items="${requestScope.movie.feedbacks}">
            <div style="padding: 10px;border: 2px solid; color: black;">
                <div style="padding-bottom: 20px">${feed.user.name}</div>
                <div style="justify-content: space-between; display: flex">
                    <div style="left: auto">${feed.text}</div>

                    <div style="right: auto">${feed.assessment}</div>
                </div>
            </div>
            <br>
        </с:forEach>
    </div>
    <br>
    <br>
    <div>
        <form action="${requestScope.request.contextPath}/add-feedback" method="post">
            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
            <input type="hidden" name="userId" value="${sessionScope.user.id}">
            <div>
                <label for="text">
                    <textarea name="text" cols="30" rows="5" id="text"
                              style="width: 100%; border: 2px solid; color: black;"
                              placeholder="<fmt:message key="page.info.feedback"/>"></textarea>
                </label>
                <br>
                <br>
                <div class="rating">
                    <label>
                        <select name="rating">
                            <option value="5">★★★★★</option>
                            <option value="4">★★★★☆</option>
                            <option value="3">★★★☆☆</option>
                            <option value="2">★★☆☆☆</option>
                            <option value="1">★☆☆☆☆</option>
                        </select>
                    </label>
                </div>
            </div>
            <br>
            <div>
                <button type="submit"><fmt:message key="save"/></button>
            </div>
        </form>
    </div>
    <br>
    <br>
    <div>
        <form action="${requestScope.request.contextPath}/movies" method="get">
            <button type="submit"><fmt:message key="page.main-page"/></button>
        </form>
    </div>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="с" uri="jakarta.tags.core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Информация о фильме</title>
</head>
<body>
<div>
    <h1>${requestScope.movie.name}</h1>
    <div style="display: flex">
        <div style="display: block; width: 50%">
            <h3>О фильме</h3>
            <div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px">Год производства:</div>
                    <div>${requestScope.movie.premierDate}</div>
                </div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px">Страна:</div>
                    <div>${requestScope.movie.country}</div>
                </div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px">Жанр:</div>
                    <div>${requestScope.movie.genre}</div>
                </div>
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width:  160px">Продюсер:</div>
                    <div>
                        <a href="${pageContext.request.contextPath}/director?directorId=${requestScope.movie.director.id}">${requestScope.movie.director.name}</a>
                    </div>
                </div>
                <div>
                    <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
                        <form action="admin/update-movie" method="get">
                            <button type="submit">Редактировать фильм</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>

        <div style="width: 50%">
            <h3>В главных ролях</h3>
            <c:forEach var="actor" items="${requestScope.movie.actors}">
                <li>
                    <a href="${pageContext.request.contextPath}/actor?actorId=${actor.id}">${actor.name}</a>
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

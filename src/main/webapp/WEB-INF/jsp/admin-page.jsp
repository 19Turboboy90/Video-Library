<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>

    <div>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <div style="margin-right: 10px">
                <form action="admin/movies-page" method="get">
                    <button type="submit">Все фильмы</button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/actors-page" method="get">
                    <button type="submit">Все актеры</button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/directors-page" method="get">
                    <button type="submit">Все режиссеры</button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/users-page" method="get">
                    <button type="submit">Все пользователи</button>
                </form>
            </div>
        </c:if>
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

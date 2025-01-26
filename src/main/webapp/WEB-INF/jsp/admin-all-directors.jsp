<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список режиссеров</title>
</head>
<body>
<div>
    <h1>Список режиссеров:</h1>
    <c:forEach var="director" items="${requestScope.directors}">
        <li>
            <a href="${pageContext.request.contextPath}/director?directorId=${director.id}">${director.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="admin" method="get">
                    <button type="submit">Добавить режиссера</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

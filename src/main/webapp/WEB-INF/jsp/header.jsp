<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
</head>
<body>
<div style="justify-content: space-between; display: flex">
    <div>
        <button>
            <a href="${pageContext.request.contextPath}/user?userId=${sessionScope.user.id}">Личная страница</a>
        </button>
    </div>
    <div style="text-align: right">
        <c:if test="${not empty sessionScope.user}">
            <div id="logout">
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit">Выйти</button>
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

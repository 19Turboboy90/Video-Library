<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of users</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1><fmt:message key="page.list.user"/>:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/users-page" method="get">
            <label for="prefix"><fmt:message key="page.search.user"/>: </label>
            <input type="text" id="prefix" name="prefix" placeholder="<fmt:message key="enter.name"/>">
            <button type="submit"><fmt:message key="filter"/></button>
            <button type="submit"><fmt:message key="cancel"/></button>
        </form>
    </div>
    <c:forEach var="user" items="${requestScope.users}">
        <li>
            <a href="${pageContext.request.contextPath}/user?userId=${user.id}">${user.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
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

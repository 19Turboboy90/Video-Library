<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>List of actors</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1><fmt:message key="page.list.actors"/>:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/actors-page" method="get">
            <label for="prefix"><fmt:message key="page.search.actor"/></label>
            <input type="text" id="prefix" name="prefix" placeholder="<fmt:message key="enter.name"/>">
            <button type="submit"><fmt:message key="filter"/></button>
            <button type="submit"><fmt:message key="cancel"/></button>
        </form>
    </div>
    <c:forEach var="actor" items="${requestScope.actors}">
        <li>
            <a href="${pageContext.request.contextPath}/actor?actorId=${actor.id}">${actor.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="${pageContext.request.contextPath}/admin/add-actor" method="get">
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

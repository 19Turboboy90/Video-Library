<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of directors</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h1><fmt:message key="page.list.director"/>:</h1>
    <div>
        <form action="${pageContext.request.contextPath}/admin/directors-page" method="get">
            <label for="prefix"><fmt:message key="page.search.director"/>:</label>
            <input type="text" id="prefix" name="prefix"
                   placeholder="<fmt:message key="enter.name"/>">
            <button type="submit"><fmt:message key="filter"/></button>
            <button type="submit"><fmt:message key="cancel"/></button>
        </form>
    </div>
    <c:forEach var="director" items="${requestScope.directors}">
        <li>
            <a href="${pageContext.request.contextPath}/director?directorId=${director.id}">${director.name}</a>
        </li>
    </c:forEach>
    <br>
    <div>
        <div style="display: flex">
            <div style="margin-right: 10px">
                <form action="${pageContext.request.contextPath}/admin/add-director" method="get">
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

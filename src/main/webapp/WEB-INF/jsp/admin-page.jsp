<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administration</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <div>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <div style="margin-right: 10px">
                <form action="admin/movies-page" method="get">
                    <button type="submit"><fmt:message key="admin.page.all.movies"/></button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/actors-page" method="get">
                    <button type="submit"><fmt:message key="admin.page.all.actors"/></button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/directors-page" method="get">
                    <button type="submit"><fmt:message key="admin.page.all.directors"/></button>
                </form>
            </div>
            <div style="margin-right: 10px">
                <form action="admin/users-page" method="get">
                    <button type="submit"><fmt:message key="admin.page.all.users"/></button>
                </form>
            </div>
        </c:if>
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

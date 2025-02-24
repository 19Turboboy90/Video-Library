<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Save actor</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>
        <c:choose>
            <c:when test="${requestScope.actor != null && requestScope.actor.id != null}">
                <fmt:message key="update"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="save"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <form action="${requestScope.request.contextPath}/admin/add-actor" method="post">
        <div>
            <input type="hidden" name="actorId" value="${requestScope.actor != null ? requestScope.actor.id : ''}">
        </div>
        <div>
            <label for="name">
                <input type="text" name="name" id="name"
                       value="${requestScope.actor != null ? requestScope.actor.name : ''}"
                       placeholder="<fmt:message key="enter.name"/>" required>
            </label>
        </div>
        <br>
        <div>
            <label for="birthday"><fmt:message key="enter.birthday"/>:
                <input type="date" name="birthday" id="birthday"
                       value="${requestScope.actor != null ? requestScope.actor.dateOfBirthday : ''}" required>
            </label>
        </div>
        <br>
        <c:choose>
            <c:when test="${requestScope.actor != null && requestScope.actor.id != null}">
                <c:set var="buttonLabel"><fmt:message key="update"/></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="buttonLabel"><fmt:message key="save"/></c:set>
            </c:otherwise>
        </c:choose>

        <button type="submit">${buttonLabel}</button>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <div>${error.message}</div>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
<br>
<br>
<div>
    <form action="${requestScope.request.contextPath}/admin" method="get">
        <button type="submit"><fmt:message key="page.main-page"/></button>
    </form>
</div>
</body>
</html>

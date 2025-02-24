<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2><fmt:message key="page.register.user"/></h2>
    <form action="${requestScope.request.contextPath}/registration" method="post">
        <div>
            <label for="name">
                <input type="text" name="name" id="name" placeholder="<fmt:message key="enter.name"/>" required>
            </label>
        </div>
        <br>
        <div>
            <label for="email">
                <input type="text" name="email" id="email" placeholder="<fmt:message key="enter.email"/>" required>
            </label>
        </div>
        <br>
        <div>
            <label for="password">
                <input type="password" name="password" id="password" placeholder="<fmt:message key="enter.password"/>"
                       required>
            </label>
        </div>
        <br>
        <br>
        <button type="submit"><fmt:message key="create"/></button>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <div>${error.message}</div>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>

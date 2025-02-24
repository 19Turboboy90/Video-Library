<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Login</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label for="email">
                <input type="text" name="email" id="email" value="${param.email}"
                       placeholder="<fmt:message key="enter.email"/>" required>
            </label>
        </div>
        <br>
        <div>
            <label for="password">
                <input type="password" name="password" id="password" value="${param.password}"
                       placeholder="<fmt:message key="enter.password"/>" required>
            </label>
        </div>
        <br>
        <div>
            <button type="submit"><fmt:message key="page.login.submit.button"/></button>
        </div>
        <br>
        <a href="${pageContext.request.contextPath}/registration">
            <button type="button"><fmt:message key="page.login.register.button"/></button>
        </a>
        <c:if test="${param.error != null}">
            <div style="color: red">
                <span><fmt:message key="page.login.error"/></span>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>

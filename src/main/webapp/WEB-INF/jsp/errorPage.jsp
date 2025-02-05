<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<div>
    <c:forEach var="error" items="${requestScope.errorMessage}">
        Code: ${error.code}
        <br>
        Error: ${error.message}
    </c:forEach>
</div>
</body>
</html>

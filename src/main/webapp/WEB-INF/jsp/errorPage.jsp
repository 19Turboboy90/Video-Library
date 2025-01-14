<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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

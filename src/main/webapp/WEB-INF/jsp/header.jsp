<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale
        value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'ru_RU')}"/>
<fmt:setBundle basename="translations"/>
<html>
<head>
    <title>Login</title>
</head>
<body>

<div>
</div>
<div style="display: flex; justify-content: space-between; width: 100%; margin: 20px auto;">
    <div style="flex-grow: 1">
        <form action="${pageContext.request.contextPath}/locale" method="post">
            <button type="submit" name="lang" value="ru_RU">RU</button>
            <button type="submit" name="lang" value="en_US">EN</button>
        </form>
    </div>
    <div style="display: flex; gap: 10px">
        <c:if test="${not empty sessionScope.user}">
            <div>
                <button>
                    <a href="${pageContext.request.contextPath}/user?userId=${sessionScope.user.id}"><fmt:message
                            key="page.header.profile.button"/></a>
                </button>
            </div>
            <div style="text-align: right">
                <div id="logout">
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button type="submit"><fmt:message key="page.header.exit.button"/></button>
                    </form>
                </div>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

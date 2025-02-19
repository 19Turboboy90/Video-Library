<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Пользователь</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div>
    <h2>Информация об пользователе</h2>
    <div>
        <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
            <div style="width: 160px">Имя:</div>
            <div>${requestScope.user.name}</div>
        </div>
        <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
            <div style="width: 160px">Почта:</div>
            <div>${requestScope.user.email}</div>
        </div>
        <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
            <div style="width: 160px">Роль:</div>
            <div>${requestScope.user.role}</div>
        </div>
        <br>
    </div>
    <div>
        <form action="${requestScope.request.contextPath}/admin/update-user" method="get" style="display: inline;">
            <input type="hidden" name="userId" value="${requestScope.user.id}">
            <button type="submit">Изменить пользователя</button>
        </form>
        <form action="${requestScope.request.contextPath}/admin/user-delete" method="post"
              style="display: inline;">
            <input type="hidden" name="userId" value="${requestScope.user.id}">
            <button type="submit">Удалить пользователя</button>
        </form>
    </div>
    <br>
    <br>
    <div>
        <c:forEach var="feed" items="${requestScope.user.feedbacks}">
            <div style="padding: 10px;border: 2px solid; color: black;">
                <div style="padding-block-start: 8px; padding-block-end: 8px; display: flex">
                    <div style="width: 160px;">Название фильма:</div>
                    <div style="padding-bottom: 20px">${feed.movie.name}</div>
                </div>

                <div style="justify-content: space-between; display: flex">
                    <div>
                        <div style="width: 160px">комментарий:</div>
                        <br>
                        <div style="left: auto">${feed.text}</div>
                    </div>
                    <div>
                        <div style="width: 160px">оценка:</div>
                        <br>
                        <div style="right: auto">${feed.assessment}</div>
                    </div>
                </div>
            </div>
            <br>
            <form action="${requestScope.feedback}/delete-feedback" method="post"
                  style="justify-content: right;display: flex">
                <input type="hidden" name="feedbackId" value="${feed.id}">
                <input type="hidden" name="userId" value="${sessionScope.user.id}">
                <button type="submit">Удалить комментарий</button>
            </form>
        </c:forEach>
    </div>
    <br>
    <br>
    <div>
        <form action="${requestScope.request.contextPath}/movies" method="get">
            <button type="submit">На главную страницу</button>
        </form>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сохранить фильм</title>
</head>
<body>
<div>
    <h2>${(requestScope.movie != null && requestScope.movie.id != null) ? "Обновить фильм" : "Сохранить фильм"}</h2>
    <form action="${requestScope.request.contextPath}/admin/add-movie" method="post">
        <div>
            <input type="hidden" name="movieId" value="${requestScope.movie != null ? requestScope.movie.id : ''}">
        </div>
        <div>
            <label for="name">Введите название фильма:
                <input type="text" name="name" id="name"
                       value="${requestScope.movie != null ? requestScope.movie.name : ''}"
                       placeholder="Введите название фильма">
            </label>
        </div>
        <br>
        <div>
            <label for="premiere_date">Введите дату релиза:
                <input type="date" name="premiere_date" id="premiere_date"
                       value="${requestScope.movie != null ? requestScope.movie.premierDate : ''}" required>
            </label>
        </div>
        <br>
        <div>
            <label for="country">Введите страну:
                <input type="text" name="country" id="country"
                       value="${requestScope.movie != null ? requestScope.movie.country : ''}"
                       placeholder="Введите страну">
            </label>
        </div>
        <br>
        <div>
            <label for="genre">Введите жанр:
                <input type="text" name="genre" id="genre"
                       value="${requestScope.movie != null ? requestScope.movie.genre : ''}"
                       placeholder="Введите жанр">
            </label>
        </div>
        <br>
        <div>
            <label for="director">Выберете режиссера:
                <select name="director" id="director" required>
                    <c:forEach items="${requestScope.directors}" var="director">
                        <option value="${director.id}">${director.name}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <br>
        <div>
            <label for="actor">Выберете съемочную группу:
                <select name="actor" id="actor" multiple required>
                    <c:forEach items="${requestScope.actors}" var="actor">
                        <option value="${actor.id}">${actor.name}</option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <br>
        <button
                type="submit">${requestScope.movie != null && requestScope.movie.id != null ? 'Редактировать' : 'Сохранить'}
        </button>
    </form>
</div>
</body>
</html>

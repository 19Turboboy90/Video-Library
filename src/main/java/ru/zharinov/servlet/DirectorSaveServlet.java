package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.CreateDirectorDto;
import ru.zharinov.exception.CreateNotFoundException;
import ru.zharinov.service.DirectorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.SAVE_DIRECTOR)
public class DirectorSaveServlet extends HttpServlet {
    private final DirectorService directorService = DirectorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.prefixPath("director-create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var director = CreateDirectorDto.builder()
                .name(req.getParameter("name"))
                .dateOfBirthday(req.getParameter("birthday"))
                .build();

        try {
            directorService.save(director);
            resp.sendRedirect("/movies");
        } catch (CreateNotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}

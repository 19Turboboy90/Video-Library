package ru.zharinov.servlet.director;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.director.CreateDirectorDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FactoryService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.SAVE_DIRECTOR)
public class DirectorSaveServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var directorId = req.getParameter("directorId");
        if (directorId != null && !directorId.isEmpty()) {
            factoryService.getDirectorService().findDirectorById(Integer.parseInt(directorId))
                    .ifPresent(director -> req.setAttribute("director", director));
        }
        req.getRequestDispatcher(JspHelper.prefixPath("director-create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var directorId = req.getParameter("directorId");
        var name = req.getParameter("name");
        var birthday = req.getParameter("birthday");

        try {
            factoryService.getDirectorService().save(CreateDirectorDto.builder()
                    .id(directorId)
                    .name(name)
                    .dateOfBirthday(birthday)
                    .build());
            resp.sendRedirect(UrlPath.ADMIN_INFO_DIRECTORS);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("director-create")).forward(req, resp);
        }
    }
}

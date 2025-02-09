package ru.zharinov.servlet.director;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FactoryService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_DELETE_DIRECTOR)
public class DirectorDeleteServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.prefixPath("admin-page")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var directorId = req.getParameter("directorId");

        try {
            if (directorId != null && !directorId.isEmpty()) {
                factoryService.getDirectorService().delete(Integer.parseInt(directorId));
            }
            resp.sendRedirect(UrlPath.ADMIN_INFO_DIRECTORS);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}

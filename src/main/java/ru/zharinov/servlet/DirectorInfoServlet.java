package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.service.DirectorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;
import ru.zharinov.validation.ErrorInfo;

import java.io.IOException;

@WebServlet(UrlPath.DIRECTOR)
public class DirectorInfoServlet extends HttpServlet {
    private final DirectorService directorService = DirectorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var directorId = Integer.parseInt(req.getParameter("directorId"));

        directorService.findDirectorById(directorId).ifPresentOrElse(
                director -> {
                    req.setAttribute("director", director);
                    try {
                        req.getRequestDispatcher(JspHelper.prefixPath("director-info")).forward(req, resp);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> {
                    var errorInTheId = ErrorInfo.of("Error in the ID", "Not found id = " + directorId);
                    req.setAttribute("errorMessage", errorInTheId);
                    try {
                        req.getRequestDispatcher(JspHelper.prefixPath("errorPage")).forward(req, resp);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}

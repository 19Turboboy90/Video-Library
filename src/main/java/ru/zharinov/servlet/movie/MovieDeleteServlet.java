package ru.zharinov.servlet.movie;

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

@WebServlet(UrlPath.ADMIN_DELETE_MOVIE)
public class MovieDeleteServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var movieId = req.getParameter("movieId");

        try {
            factoryService.getMovieService().deleteMovieById(Integer.parseInt(movieId));
            resp.sendRedirect(UrlPath.MOVIES);
        } catch (NotFoundException e) {
            req.setAttribute("errorMessage", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("errorPage")).forward(req, resp);
        }
    }
}

package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.service.MovieService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.MOVIES)
public class MainPageServlet extends HttpServlet {
    private final MovieService movieService = MovieService.getInstance();
    private static final Integer MIN_DATE = 1800;
    private static final Integer MAX_DATE = 3000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var fromDate = req.getParameter("fromDate");
        var toDate = req.getParameter("toDate");
        var from = (fromDate == null || fromDate.isEmpty()) ? MIN_DATE : Integer.parseInt(fromDate);
        var to = (toDate == null || toDate.isEmpty()) ? MAX_DATE : Integer.parseInt(toDate);

        var allMoviesByDate = movieService.findAllMoviesByDate(from, to);
        req.setAttribute("movies", allMoviesByDate);
        req.getRequestDispatcher(JspHelper.prefixPath("main-page")).forward(req, resp);
    }
}


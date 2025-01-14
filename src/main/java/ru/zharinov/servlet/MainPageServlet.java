package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.service.MovieService;
import ru.zharinov.util.JspHelper;

import java.io.IOException;

@WebServlet("/movies")
public class MainPageServlet extends HttpServlet {
    private final MovieService movieService = MovieService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var allMovies = movieService.findAllMovies();
        req.setAttribute("movies", allMovies);
        req.getRequestDispatcher(JspHelper.prefixPath("main-page")).forward(req, resp);
    }
}

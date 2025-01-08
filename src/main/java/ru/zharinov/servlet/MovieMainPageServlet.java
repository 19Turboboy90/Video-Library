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
public class MovieMainPageServlet extends HttpServlet {
    private final MovieService movieService = MovieService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("movies", movieService.findAllMovies());
        req.getRequestDispatcher(JspHelper.prefixPath("movies-main-page")).forward(req, resp);
    }
}

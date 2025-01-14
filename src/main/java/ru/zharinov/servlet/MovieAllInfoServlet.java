package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.exception.CreateNotFoundException;
import ru.zharinov.exception.ErrorInfo;
import ru.zharinov.service.MovieService;
import ru.zharinov.util.JspHelper;

import java.io.IOException;

@WebServlet("/movie")
public class MovieAllInfoServlet extends HttpServlet {
    private final MovieService movieService = MovieService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var parameter = req.getParameter("movieId");
        int movieId = Integer.parseInt(parameter);

        movieService.findMovieById(movieId).ifPresentOrElse(
                movie -> {
                    req.setAttribute("movie", movie);
                    try {
                        req.getRequestDispatcher(JspHelper.prefixPath("movie-all-info")).forward(req, resp);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> {
                    CreateNotFoundException errors = new CreateNotFoundException();
                    errors.add(ErrorInfo.of("Error in the ID", "Not found id = " + movieId));
                    req.setAttribute("errorMessage", errors.getErrors());

                    try {
                        req.getRequestDispatcher(JspHelper.prefixPath("errorPage")).forward(req, resp);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}

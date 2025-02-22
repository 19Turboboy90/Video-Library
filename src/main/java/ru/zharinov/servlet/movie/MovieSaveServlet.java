package ru.zharinov.servlet.movie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.zharinov.dto.movie.CreateMovieDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FactoryService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_SAVE_MOVIE)
public class MovieSaveServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var movieId = req.getParameter("movieId");
        if (movieId != null && !movieId.isEmpty()) {
            factoryService.getMovieService().findById(Integer.parseInt(movieId))
                    .ifPresent(movie -> req.setAttribute("movie", movie));
        }
        var allActor = factoryService.getActorService().findAllActor();
        req.setAttribute("actors", allActor);
        var allDirectors = factoryService.getDirectorService().findAllDirectors();
        req.setAttribute("directors", allDirectors);
        req.getRequestDispatcher(JspHelper.prefixPath("movie-create")).forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var movieId = req.getParameter("movieId");
        var name = req.getParameter("name");
        var premiereDate = req.getParameter("premiere_date");
        var country = req.getParameter("country");
        var genre = req.getParameter("genre");
        var director = req.getParameter("director");
        var actors = req.getParameterValues("actor");

        try {
            factoryService.getMovieService().saveOrUpdateMovie(CreateMovieDto.builder()
                    .id(movieId)
                    .name(name)
                    .premiereDate(premiereDate)
                    .country(country)
                    .genre(genre)
                    .directorId(director)
                    .actorsId(actors)
                    .build());
            resp.sendRedirect(UrlPath.MOVIES);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("actor-create")).forward(req, resp);
        }
    }
}

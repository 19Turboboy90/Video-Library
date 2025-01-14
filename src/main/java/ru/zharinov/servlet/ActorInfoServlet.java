package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.exception.CreateNotFoundException;
import ru.zharinov.exception.ErrorInfo;
import ru.zharinov.service.ActorService;
import ru.zharinov.util.JspHelper;

import java.io.IOException;

@WebServlet("/actor")
public class ActorInfoServlet extends HttpServlet {
    private final ActorService actorService = ActorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var actorId = Integer.parseInt(req.getParameter("actorId"));
        actorService.findActorByMovieId(actorId).ifPresentOrElse(
                actor -> {
                    req.setAttribute("actor", actor);
                    try {
                        req.getRequestDispatcher(JspHelper.prefixPath("actor-info")).forward(req, resp);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> {
                    CreateNotFoundException errors = new CreateNotFoundException();
                    errors.add(ErrorInfo.of("Error in the ID", "Not found id = " + actorId));
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

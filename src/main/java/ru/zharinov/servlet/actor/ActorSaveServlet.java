package ru.zharinov.servlet.actor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.actor.CreateActorDto;
import ru.zharinov.exception.CreateNotFoundException;
import ru.zharinov.service.ActorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.SAVE_ACTOR)
public class ActorSaveServlet extends HttpServlet {
    private final ActorService actorService = ActorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.prefixPath("actor-create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var actor = CreateActorDto.builder()
                .name(req.getParameter("name"))
                .dateOfBirthday(req.getParameter("birthday"))
                .build();

        try {
            actorService.save(actor);
            resp.sendRedirect("/movies");
        } catch (CreateNotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}

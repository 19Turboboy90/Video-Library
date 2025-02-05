package ru.zharinov.servlet.actor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.actor.CreateOrUpdateActorDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.ActorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.SAVE_ACTOR)
public class ActorSaveServlet extends HttpServlet {
    private final ActorService actorService = ActorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var actorId = req.getParameter("actorId");
        if (actorId != null && !actorId.isEmpty()) {
            var actor = actorService.findActorById(Integer.parseInt(actorId));
            req.setAttribute("actor", actor);
        }
        req.getRequestDispatcher(JspHelper.prefixPath("actor-create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = req.getParameter("actorId");
        var name = req.getParameter("name");
        var birthday = req.getParameter("birthday");

        try {
            actorService.save(CreateOrUpdateActorDto.builder()
                    .id(id)
                    .name(name)
                    .dateOfBirthday(birthday)
                    .build());
            resp.sendRedirect(UrlPath.ADMIN_INFO_ACTORS);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("actor-create")).forward(req, resp);
        }
    }
}

package ru.zharinov.servlet.actor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.ActorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_DELETE_ACTOR)
public class ActorDeleteServlet extends HttpServlet {
    private final ActorService actorService = ActorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.prefixPath("admin-page")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var actorId = req.getParameter("actorId");

        try {
            if (actorId != null && !actorId.isEmpty()) {
                actorService.delete(Integer.parseInt(actorId));
            }
            resp.sendRedirect(UrlPath.ADMIN_INFO_ACTORS);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}

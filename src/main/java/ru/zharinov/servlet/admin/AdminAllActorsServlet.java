package ru.zharinov.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.service.ActorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_INFO_ACTORS)
public class AdminAllActorsServlet extends HttpServlet {
    private final ActorService actorService = ActorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var param = req.getParameter("prefix");
        String prefix = param == null || param.isEmpty() ? "" : param;
        req.setAttribute("actors", actorService.findActorsByPrefix(prefix));
        req.getRequestDispatcher(JspHelper.prefixPath("admin-all-actors")).forward(req, resp);
    }
}

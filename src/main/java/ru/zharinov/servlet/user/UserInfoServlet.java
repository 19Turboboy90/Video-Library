package ru.zharinov.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FactoryService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.USER)
public class UserInfoServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = Integer.parseInt(req.getParameter("userId"));

        try {
            factoryService.getUserService().findUserById(userId).ifPresent(user -> req.setAttribute("user", user));
            req.getRequestDispatcher(JspHelper.prefixPath("user-info")).forward(req, resp);
        } catch (NotFoundException e) {
            req.setAttribute("errorMessage", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("errorPage")).forward(req, resp);
        }
    }
}

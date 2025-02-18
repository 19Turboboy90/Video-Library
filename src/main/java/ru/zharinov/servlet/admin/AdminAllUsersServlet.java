package ru.zharinov.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.service.FactoryService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_INFO_USERS)
public class AdminAllUsersServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var prefix = req.getParameter("prefix");
        req.setAttribute("users", factoryService.getUserService().findAllUsersByPrefix(prefix));
        req.getRequestDispatcher(JspHelper.prefixPath("admin-all-users")).forward(req, resp);
    }
}

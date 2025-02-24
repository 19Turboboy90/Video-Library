package ru.zharinov.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.user.CreateUserDto;
import ru.zharinov.entity.Role;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FactoryService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_UPDATE_USER)
public class AdminUserUpdateServlet extends HttpServlet {
    private final FactoryService factoryService = FactoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        var userId = req.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
            factoryService.getUserService().findUserById(Integer.parseInt(userId)).ifPresent(user -> req.setAttribute("user", user));
        }
        req.getRequestDispatcher(JspHelper.prefixPath("admin-update-user")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .id(req.getParameter("userId"))
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .build();

        try {
            factoryService.getUserService().updateUser(userDto);
            resp.sendRedirect(UrlPath.ADMIN_INFO_USERS);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("admin_update-user")).forward(req, resp);
        }
    }
}

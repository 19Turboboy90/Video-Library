package ru.zharinov.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.service.DirectorService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.ADMIN_INFO_DIRECTORS)
public class AdminAllDirectorsServlet extends HttpServlet {
    private final DirectorService directorService = DirectorService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("directors", directorService.findAllDirectors());
        req.getRequestDispatcher(JspHelper.prefixPath("admin-all-directors")).forward(req, resp);
    }
}

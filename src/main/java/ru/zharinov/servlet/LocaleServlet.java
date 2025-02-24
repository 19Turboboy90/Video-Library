package ru.zharinov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOCALE)
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var lang = req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);

        var prevPage = req.getHeader("referer");
        var page = prevPage != null ? prevPage : UrlPath.LOGIN;
        resp.sendRedirect(page);
    }
}

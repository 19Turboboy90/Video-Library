package ru.zharinov.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.UserDto;
import ru.zharinov.entity.Role;

import java.io.IOException;
import java.util.Set;

import static ru.zharinov.util.UrlPath.*;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    private static final Set<String> PRIVATE_URL = Set.of(SAVE_MOVIE, SAVE_ACTOR, SAVE_DIRECTOR);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var req = (HttpServletRequest) servletRequest;
        var resp = (HttpServletResponse) servletResponse;
        var requestURI = req.getRequestURI();
        var session = req.getSession();
        if (session != null) {
            var user = (UserDto) session.getAttribute("user");
            if (user.getRole() == Role.ADMIN && isPrivatePath(requestURI)) {
                filterChain.doFilter(req, resp);
            }
        }

        resp.sendRedirect("/movies");
    }

    private boolean isPrivatePath(String requestURI) {
        return PRIVATE_URL.stream().anyMatch(requestURI::startsWith);
    }
}
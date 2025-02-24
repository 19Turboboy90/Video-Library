package ru.zharinov.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.user.UserDto;
import ru.zharinov.entity.Role;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;
import ru.zharinov.validation.ErrorInfo;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static ru.zharinov.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, LOCALE);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var requestURI = request.getRequestURI();

        if (isPublicPath(requestURI) || isUserLoggedIn(request)) {
            if (requiresAuthorization(requestURI)) {
                handleAuthorization(request, response, filterChain);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            response.sendRedirect(LOGIN);
        }
    }

    private boolean isPublicPath(String requestURI) {
        return PUBLIC_PATH.stream().anyMatch(requestURI::startsWith);
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    private boolean requiresAuthorization(String requestURI) {
        return requestURI.startsWith(UrlPath.USER); // Ограниченная страница
    }

    private void handleAuthorization(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        var sessionUser = (UserDto) request.getSession().getAttribute("user");
        var userIdParam = request.getParameter("userId");

        if (userIdParam == null || sessionUser == null) {
            response.sendRedirect(LOGIN);
            return;
        }

        int userId = Integer.parseInt(userIdParam);

        if (sessionUser.getRole() != Role.ADMIN && !sessionUser.getId().equals(userId)) {
            request.setAttribute("errorMessage",
                    List.of(ErrorInfo.of("Access error", "Access denied")));
            request.getRequestDispatcher(JspHelper.prefixPath("errorPage")).forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

package ru.zharinov.servlet.feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FeedbackService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.DELETE_FEEDBACK)
public class FeedbackDeleteServlet extends HttpServlet {
    private final FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var feedbackId = req.getParameter("feedbackId");
        var userId = req.getParameter("userId");

        try {
            feedbackService.deleteFeedbackById(Integer.parseInt(feedbackId));
            resp.sendRedirect(UrlPath.USER + "?userId=" + userId);
        } catch (NotFoundException e) {
            req.setAttribute("errorMessage", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("errorPage")).forward(req, resp);
        }
    }
}

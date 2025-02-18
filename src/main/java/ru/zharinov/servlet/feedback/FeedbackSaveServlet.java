package ru.zharinov.servlet.feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.zharinov.dto.feedback.CreateFeedbackDto;
import ru.zharinov.exception.NotFoundException;
import ru.zharinov.service.FeedbackService;
import ru.zharinov.util.JspHelper;
import ru.zharinov.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.SAVE_FEEDBACK)
public class FeedbackSaveServlet extends HttpServlet {
    private final FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var text = req.getParameter("text");
        var userId = req.getParameter("userId");
        var movieId = req.getParameter("movieId");
        var rating = req.getParameter("rating");

        try {
            feedbackService.save(CreateFeedbackDto.builder()
                    .text(text)
                    .assessment(rating)
                    .movieId(movieId)
                    .userId(userId)
                    .build());
            resp.sendRedirect(UrlPath.MOVIE + "?movieId=" + movieId);
        } catch (NotFoundException e) {
            req.setAttribute("errors", e.getErrors());
            req.getRequestDispatcher(JspHelper.prefixPath("movie-all-info")).forward(req, resp);
        }
    }
}

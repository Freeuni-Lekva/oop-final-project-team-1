package servlets;

import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.QuizAttempt;


import java.io.IOException;
import java.util.List;
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {





    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");
        String username = (String) session.getAttribute("userName");
        try {
            List<QuizAttempt> fullHistory = quizDAO.getUserQuizHistory(username, Integer.MAX_VALUE);
            req.setAttribute("fullHistory", fullHistory);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/HistoryPage.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

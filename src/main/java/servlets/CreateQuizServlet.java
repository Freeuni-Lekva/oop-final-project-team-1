package servlets;


import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.AccountManager;
import models.Messages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("quizTitle");
        int numQuestions = Integer.parseInt(request.getParameter("numQuestions"));
        String quizType = request.getParameter("quizType");
        String username = (String) request.getSession().getAttribute("userName");
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");
        int quizId = 0;
        try {
            quizId = quizDAO.insertQuiz(title, username, 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("addingQuestions.jsp?quizId=" + quizId + "&type=" + quizType + "&num=" + numQuestions);

    }
}


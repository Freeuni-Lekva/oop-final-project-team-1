package servlets;


import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("quizTitle");

        String username = (String) request.getSession().getAttribute("userName");
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");

        int quizId = 0;
        try {

            quizId = quizDAO.insertQuiz(title, username, 0,Boolean.parseBoolean(request.getParameter("isRandom")));
            request.setAttribute("quizId", quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("addingQuestions.jsp");
        requestDispatcher.forward(request,response);


    }
}


package servlets;


import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Option;
import models.Questions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");

        try {
            List<Questions> questions = quizDAO.getQuestionsForQuiz(quizId);
            HttpSession session = request.getSession();
            session.setAttribute("questions", questions);
            session.setAttribute("quizId", quizId);
            request.setAttribute("index", 0);
            if(quizDAO.isRandom(quizId)) Collections.shuffle(questions);
            RequestDispatcher dispatcher = request.getRequestDispatcher("takingQuiz.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error loading quiz", e);
        }
    }

}


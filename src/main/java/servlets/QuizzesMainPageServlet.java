package servlets;

import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Quiz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Quizzes")
public class QuizzesMainPageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ArrayList<Quiz> quizzes = (ArrayList<Quiz>) request.getServletContext().getAttribute("quizzes");
        ArrayList<Quiz> popularQuizzes = (ArrayList<Quiz>) request.getServletContext().getAttribute("popularQuizzes");
        QuizDAO quizDAO= (QuizDAO) request.getServletContext().getAttribute("quizDAO");
        try {
            quizzes= quizDAO.getQuizList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            popularQuizzes= quizDAO.getPopularQuizzes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher rd=getServletContext().getRequestDispatcher("/Quizzes.jsp");
        rd.forward(request, response);
    }
    }


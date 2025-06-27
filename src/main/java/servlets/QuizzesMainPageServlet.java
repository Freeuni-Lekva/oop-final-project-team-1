package servlets;

import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Quiz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Quizzes")
public class QuizzesMainPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("userName");
        ArrayList<Quiz> quizzes = (ArrayList<Quiz>) request.getServletContext().getAttribute("quizzes");
        ArrayList<Quiz> popularQuizzes = (ArrayList<Quiz>) request.getServletContext().getAttribute("popularQuizzes");
        ArrayList<Quiz> recentlyTakenQuizzes = (ArrayList<Quiz>) request.getServletContext().getAttribute("recentlyTakenQuizzes");
        ArrayList<Quiz> recentlyCreatedQuizzes = (ArrayList<Quiz>) request.getServletContext().getAttribute("recentlyCreatedQuizzes");
        QuizDAO quizDAO= (QuizDAO) request.getServletContext().getAttribute("quizDAO");
        try {
            quizzes= quizDAO.getQuizList();
            popularQuizzes= quizDAO.getPopularQuizzes();
            recentlyTakenQuizzes = quizDAO.getRecentlyTakenQuizzes(currentUser);
            recentlyCreatedQuizzes = quizDAO.getRecentlyCreatedQuizzes();
            request.setAttribute("quizzes", quizzes);
            request.setAttribute("popularQuizzes", popularQuizzes);
            request.setAttribute("recentlyTakenQuizzes", recentlyTakenQuizzes);
            request.setAttribute("recentlyCreatedQuizzes", recentlyCreatedQuizzes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher rd=getServletContext().getRequestDispatcher("/Quizzes.jsp");
        rd.forward(request, response);
    }
    }


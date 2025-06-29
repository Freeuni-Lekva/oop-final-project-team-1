package servlets;


import dao.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Option;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/addingQuestionsServlet")
public class addingQuestionsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        String quizType = request.getParameter("quizType");
        int numQuestions = Integer.parseInt(request.getParameter("numQuestions"));

        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");

        try {
            for (int i = 1; i <= numQuestions; i++) {
                String questionText = request.getParameter("questionText" + i);

                switch (quizType) {
                    case "response": {
                        String correctAnswer = request.getParameter("correctAnswer" + i);
                        quizDAO.insertResponseQuestion(quizId, questionText, correctAnswer);
                        break;
                    }
                    case "fill": {
                        String correctAnswer = request.getParameter("correctAnswer" + i);
                        quizDAO.insertFillInBlankQuestion(quizId, questionText, correctAnswer);
                        break;
                    }
                    case "picture": {
                        String imageUrl = request.getParameter("imageUrl" + i);
                        String correctAnswer = request.getParameter("correctAnswer" + i);
                        quizDAO.insertPictureQuestion(quizId, questionText, imageUrl, correctAnswer);
                        break;
                    }
                    case "mcq": {
                        ArrayList<Option> options = new ArrayList<>();
                        int correctOption = Integer.parseInt(request.getParameter("correctOption" + i));

                        for (int j = 1; j <= 4; j++) {
                            String optionText = request.getParameter("option" + j + "_" + i);
                            boolean isCorrect = (j == correctOption);
                            options.add(new Option(0, optionText, isCorrect));
                        }

                        quizDAO.insertMultipleChoiceQuestion(quizId, questionText, options);
                        break;
                    }
                }
            }

            response.sendRedirect("HomePage.jsp");
        } catch (SQLException e) {
            throw new ServletException("Failed to save questions", e);
        }
    }
}


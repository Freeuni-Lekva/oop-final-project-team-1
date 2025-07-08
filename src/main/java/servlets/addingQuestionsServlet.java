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
import java.util.IllegalFormatCodePointException;

@WebServlet("/addingQuestionsServlet")
public class addingQuestionsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int quizId = Integer.parseInt(request.getParameter("quizId"));

        int numResponseQuestions = Integer.parseInt(request.getParameter("numResponseQuestions"));
        int numFillQuestions = Integer.parseInt(request.getParameter("numFillQuestions"));
        int numPictureQuestions = Integer.parseInt(request.getParameter("numPictureQuestions"));
        int numMCQuestions = Integer.parseInt(request.getParameter("numMCQuestions"));
        int numQuestions = numResponseQuestions + numFillQuestions + numPictureQuestions + numMCQuestions;
                QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");
                
        try {
            for (int i = 1; i <= numQuestions; i++) {
                String questionText = request.getParameter("questionText" + i);

                if (i-1 < numFillQuestions) {
                    String correctAnswer = request.getParameter("correctAnswer" + i);
                    quizDAO.insertFillInBlankQuestion(quizId, questionText, correctAnswer);

                } else if (i-1 < numFillQuestions + numPictureQuestions) {
                    String imageUrl = request.getParameter("imageUrl" + i);
                    String correctAnswer = request.getParameter("correctAnswer" + i);
                    quizDAO.insertPictureQuestion(quizId, questionText, imageUrl, correctAnswer);

                } else if (i-1 < numQuestions - numMCQuestions) {
                    String correctAnswer = request.getParameter("correctAnswer" + i);
                    quizDAO.insertResponseQuestion(quizId, questionText, correctAnswer);

                } else {
                    ArrayList<Option> options = new ArrayList<>();
                    int correctOption = Integer.parseInt(request.getParameter("correctAnswer" + i));

                    for (int j = 1; j <= 4; j++) {
                        String optionText = request.getParameter("option" + j + "_" + i);
                        boolean isCorrect = (j == correctOption);
                        options.add(new Option(0, optionText, isCorrect));
                    }

                    quizDAO.insertMultipleChoiceQuestion(quizId, questionText, options);
                }
            }
            response.sendRedirect("HomePage.jsp");
        } catch (SQLException e) {
            throw new ServletException("Failed to save questions", e);
        }
    }
}


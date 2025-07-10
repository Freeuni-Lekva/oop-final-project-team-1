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

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    List<Questions> questions = (List<Questions>) session.getAttribute("questions");
        int score = 0;
        String username = (String) session.getAttribute("userName");
        int quizId = (Integer) session.getAttribute("quizId");

        QuizDAO quizDAO= (QuizDAO) request.getServletContext().getAttribute("quizDAO");
        boolean isSinglePage = Boolean.parseBoolean(request.getParameter("isSinglePage"));
        boolean isImmediateFeedback = Boolean.parseBoolean(request.getParameter("immediateFeedback"));
        int index = 0;
        if(isSinglePage) {
            index = questions.size()-1;
        }else {
            index = Integer.parseInt(request.getParameter("index")) - 1;
        }
       if(!isSinglePage){
           score=Integer.parseInt(request.getParameter("score"));
           String userAnswer = request.getParameter("answer_" + questions.get(index).getId());
           if(isAnswerCorrect(questions.get(index).getCorrectAnswer(),userAnswer))score++;

       }else {


           for (Questions question : questions) {

               String userAnswer = request.getParameter("answer_" + question.getId());
               String correctAnswer = question.getCorrectAnswer();

               if (userAnswer != null && isAnswerCorrect(userAnswer, correctAnswer)) {
                   score++;

               }

           }
       }



        request.setAttribute("score", score);
        request.setAttribute("maxScore", questions.size());

        RequestDispatcher rd;
        if(index==questions.size()-1) {
            try {
                quizDAO.insertScore(quizId,username,score);
                quizDAO.incrementTimesTaken(quizId);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rd = getServletContext().getRequestDispatcher("/Score.jsp");

        }else {
            rd = getServletContext().getRequestDispatcher("/takingQuiz.jsp");
        }
        rd.forward(request, response);
    }

    public boolean isAnswerCorrect(String userAnswer, String correctAnswer) {
        if (userAnswer == null || correctAnswer == null) return false;

        try {
            double correctNum = Double.parseDouble(correctAnswer.trim());
            double userNum = Double.parseDouble(userAnswer.trim());
            return correctNum == userNum;
        } catch (NumberFormatException e) {

        }

        String normalizedUser = normalize(userAnswer);
        String normalizedCorrect = normalize(correctAnswer);


        if (normalizedUser.equals(normalizedCorrect)) return true;


        int distance = levenshtein(normalizedUser, normalizedCorrect);
        if (distance <= 2) return true;


        String abbrevCorrect = abbreviate(normalizedCorrect);
        String abbrevUser = abbreviate(normalizedUser);
        return abbrevUser.equals(abbrevCorrect);
    }
    private String normalize(String input) {
        return input.trim()
                .toLowerCase()
                .replaceAll("[^a-z ]", "")
                .replaceAll("\\s+", " ");
    }
    private String abbreviate(String input) {
        String[] words = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(word.charAt(0));
            }
        }
        return sb.toString();
    }
    public int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[a.length()][b.length()];
    }


}


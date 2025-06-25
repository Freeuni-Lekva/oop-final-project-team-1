package dao;

import models.*;

import java.sql.*;
import java.util.*;

public class QuizDAO {

    private Connection connection;
    private final int numberOfQuizzesShown=5;
    public QuizDAO(Connection connection) {
        this.connection = connection;
    }


    public ArrayList<Quiz> getRecentQuizzes(String username) throws SQLException {
        ArrayList<Quiz> recentQuizes = new ArrayList<>();
        String sql="SELECT q.quizId, q.title, q.timesTaken FROM Score s JOIN Quiz q ON s.quizId = q.quizId WHERE s.username = ? ORDER BY s.attemptTime DESC LIMIT ?" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setInt(2, numberOfQuizzesShown);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Quiz quiz = new Quiz(rs.getString("title"), rs.getString("quizID"), rs.getInt("timesTaken"));
            recentQuizes.add(quiz);
        }




        return recentQuizes;
    }
    public ArrayList<Quiz> getQuizzes() throws SQLException {
        ArrayList<Quiz> recentQuizzes = getQuizzes();
        if(recentQuizzes.size() > numberOfQuizzesShown) {
            return (ArrayList<Quiz>) recentQuizzes.subList(recentQuizzes.size()-numberOfQuizzesShown, recentQuizzes.size());
        }
        return recentQuizzes;
    }
    public ArrayList<Quiz>getPopularQuizzes() throws SQLException {
       ArrayList<Quiz> popularQuizzes=getQuizList();

        Collections.sort(popularQuizzes);
        popularQuizzes= (ArrayList<Quiz>) popularQuizzes.subList(0,Math.min(popularQuizzes.size(), numberOfQuizzesShown));
       return popularQuizzes;
    }
    public ArrayList<Quiz> getQuizList() throws SQLException {
        String sql="SELECT * FROM quiz";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Quiz> quizzList = new ArrayList<>();
        while(resultSet.next()) {
           Quiz quiz = new Quiz(resultSet.getString("title"), resultSet.getString("quizID"), resultSet.getInt("timesTaken"));
           quizzList.add(quiz);
        }

        return quizzList;
    }


    public List<Questions> getQuestionsForQuiz(int quizId) throws SQLException {
        ArrayList<Questions> questions = new ArrayList<>();

        String sql = "SELECT * FROM Question WHERE quizId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, quizId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int questionId = rs.getInt("questionId");
            String questionText = rs.getString("questionText");
            String type = rs.getString("questionType");

            switch (type) {
                case "response":
                    questions.add(loadQuestionResponse(questionId, questionText));
                    break;
                case "fill":
                    questions.add(loadFillInBlank(questionId, questionText));
                    break;
                case "picture":
                    questions.add(loadPictureResponse(questionId, questionText));
                    break;
                case "mcq":
                    questions.add(loadMultipleChoice(questionId, questionText));
                    break;
            }
        }
        return questions;
    }

    private QuestionResponse loadQuestionResponse(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM QuestionResponse WHERE questionId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String correctAnswer = rs.getString("correctAnswer");
        return new QuestionResponse(questionId, questionText, correctAnswer);
    }

    private FillInBlank loadFillInBlank(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM FillInBlankQuestion WHERE questionId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String correctAnswer = rs.getString("correctAnswer");
        return new FillInBlank(questionId, questionText, correctAnswer);
    }

    private PictureResponse loadPictureResponse(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM PictureQuestion WHERE questionId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String imageUrl = rs.getString("imageUrl");
        String correctAnswer = rs.getString("correctAnswer");
        return new PictureResponse(questionId, questionText, imageUrl, correctAnswer);
    }

    private MultipleChoice loadMultipleChoice(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM MultipleChoiceOptiom WHERE questionId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();

        ArrayList<Option> options = new ArrayList<>();
        while (rs.next()) {
            int optionId = rs.getInt("optionId");
            String optionText = rs.getString("optionText");
            boolean isCorrect = rs.getBoolean("isCorrect");
            options.add(new Option(optionId, optionText, isCorrect));
        }
        return new MultipleChoice(questionId, questionText, options);
    }
}
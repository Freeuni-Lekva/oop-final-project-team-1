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

 public int insertQuiz(String title, String creatorUsername, int timeLimitSec, boolean randQuiz) throws SQLException {
        int creatorID = getUserIdByUsername(creatorUsername);

        String sql = "INSERT INTO Quiz (title, creatorUsername, creatorID, timeLimitSec, randomQuiz) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, title);
        stmt.setString(2, creatorUsername);
        stmt.setInt(3, creatorID);
        stmt.setInt(4, timeLimitSec);
        stmt.setBoolean(5, randQuiz);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Failed to retrieve generated quiz ID.");
        }
    }

    public int insertQuestion(int quizId, String type, String questionText) throws SQLException {
        String sql = "INSERT INTO Question (quizId, type, text) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, quizId);
        stmt.setString(2, type);
        stmt.setString(3, questionText);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Failed to retrieve generated question ID.");
        }
    }

    public void insertResponseQuestion(int quizId, String questionText, String answer) throws SQLException {
        int questionId = insertQuestion(quizId, "response", questionText);
        String sql = "INSERT INTO QuestionResponse (questionId, Answer) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        stmt.setString(2, answer);
        stmt.executeUpdate();
    }

    public void insertFillInBlankQuestion(int quizId, String questionText, String answer) throws SQLException {
        int questionId = insertQuestion(quizId, "fill", questionText);
        String sql = "INSERT INTO FillInBlankQuestion (questionId, Answer) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        stmt.setString(2, answer);
        stmt.executeUpdate();
    }

    public void insertPictureQuestion(int quizId, String questionText, String imageUrl, String answer) throws SQLException {
        int questionId = insertQuestion(quizId, "picture", questionText);
        String sql = "INSERT INTO PictureQuestion (questionId, imageUrl, Answer) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        stmt.setString(2, imageUrl);
        stmt.setString(3, answer);
        stmt.executeUpdate();
    }

    public void insertMCQOption(int questionId, String optionText, boolean isCorrect) throws SQLException {
        String sql = "INSERT INTO MultipleChoiceOption (questionId, optionText, isCorrect) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        stmt.setString(2, optionText);
        stmt.setBoolean(3, isCorrect);
        stmt.executeUpdate();
    }
    private void insertMultipleChoiceMeta(int questionId) throws SQLException {
        String sql = "INSERT INTO MultipleChoiceQuestion (questionId) VALUES (?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        stmt.executeUpdate();
    }
    public void insertMultipleChoiceQuestion(int quizId, String questionText, List<Option> options) throws SQLException {
        int questionId = insertQuestion(quizId, "mcq", questionText);
        insertMultipleChoiceMeta(questionId);
        for (Option option : options) {
            insertMCQOption(questionId, option.getOptionText(), option.isCorrect());
        }
    }

    public ArrayList<Quiz> getRecentlyTakenQuizzes(String username) throws SQLException {
        ArrayList<Quiz> recentQuizes = new ArrayList<>();
        String sql="SELECT q.quizId, q.title, q.timesTaken ,q.creatorUsername FROM Score s JOIN Quiz q ON s.quizId = q.quizId WHERE s.username = ? ORDER BY s.attemptTime DESC LIMIT ?" ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setInt(2, numberOfQuizzesShown);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            Quiz quiz = new Quiz(rs.getString("title"), rs.getString("quizID"), rs.getInt("timesTaken"),rs.getString("creatorUsername"));
            recentQuizes.add(quiz);
        }
        return recentQuizes;
    }
    public ArrayList<Quiz> getRecentlyCreatedQuizzes() throws SQLException {
        ArrayList<Quiz> recentQuizzes = getQuizList();
        if(recentQuizzes.size() > numberOfQuizzesShown) {
            return new ArrayList<>(recentQuizzes.subList(recentQuizzes.size()-numberOfQuizzesShown, recentQuizzes.size()));
        }
        return recentQuizzes;
    }
    public ArrayList<Quiz>getPopularQuizzes() throws SQLException {
       ArrayList<Quiz> popularQuizzes=getQuizList();

        Collections.sort(popularQuizzes);
        popularQuizzes= new ArrayList<>( popularQuizzes.subList(0,Math.min(popularQuizzes.size(), numberOfQuizzesShown)));
       return popularQuizzes;
    }
    public ArrayList<Quiz> getQuizList() throws SQLException {
        String sql="SELECT * FROM quiz";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Quiz> quizzList = new ArrayList<>();
        while(resultSet.next()) {
           Quiz quiz = new Quiz(resultSet.getString("title"), resultSet.getString("quizID"), resultSet.getInt("timesTaken"), resultSet.getString("creatorUsername"));
           quizzList.add(quiz);
        }

        return quizzList;
    }
    public boolean isRandom(int quizId) throws SQLException {
        String sql="SELECT * FROM quiz WHERE quizId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, quizId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getBoolean("randomQuiz");
        }
        throw new RuntimeException("Quiz id not found");
    }

    public List<Questions> getQuestionsForQuiz(int quizId) throws SQLException {
        ArrayList<Questions> questions = new ArrayList<>();

        String sql = "SELECT * FROM Question WHERE quizId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, quizId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int questionId = rs.getInt("questionId");
            String questionText = rs.getString("text");
            String type = rs.getString("type");

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
        String correctAnswer = rs.getString("Answer");
        return new QuestionResponse(questionId, questionText, correctAnswer);
    }

    private FillInBlank loadFillInBlank(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM FillInBlankQuestion WHERE questionId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String correctAnswer = rs.getString("Answer");
        return new FillInBlank(questionId, questionText, correctAnswer);
    }

    private PictureResponse loadPictureResponse(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM PictureQuestion WHERE questionId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String imageUrl = rs.getString("imageUrl");
        String correctAnswer = rs.getString("Answer");
        return new PictureResponse(questionId, questionText, imageUrl, correctAnswer);
    }

    private MultipleChoice loadMultipleChoice(int questionId, String questionText) throws SQLException {
        String sql = "SELECT * FROM MultipleChoiceOption WHERE questionId = ?";
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
    public void insertScore(int quizId, String username, int score) throws SQLException {
        int userId = getUserIdByUsername(username);

        String sql = "INSERT INTO Score (quizId, username, userId, score) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, quizId);
        stmt.setString(2, username);
        stmt.setInt(3, userId);
        stmt.setInt(4, score);
        stmt.executeUpdate();
    }
    public List<Map<String, Object>> getTopScorersForQuiz(int quizId) throws SQLException {
        String sql = "SELECT * FROM (SELECT username, score, RANK() OVER (ORDER BY score DESC, attemptTime ASC) AS `rank` FROM Score WHERE quizId = ?) ranked WHERE `rank` <= 3";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, quizId);
        ResultSet rs = stmt.executeQuery();

        List<Map<String, Object>> topScorers = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("username", rs.getString("username"));
            entry.put("score", rs.getInt("score"));
            topScorers.add(entry);
        }
        return topScorers;
    }
    public int getHighestScoreForUser(int quizId, String username) throws SQLException {
        String sql = "SELECT MAX(score) as maxScore FROM Score WHERE quizId = ? AND username = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, quizId);
        stmt.setString(2, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("maxScore");
        }
        return 0;
    }

    private int getUserIdByUsername(String username) throws SQLException {
        String sql = "SELECT userId FROM Users WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("userId");
        } else {
            throw new SQLException("User not found for username: " + username);
        }
    }


    public ArrayList<Quiz> getAllQuizzes() throws SQLException {
        String sql = "SELECT quizId, title, timesTaken, creatorUsername, creatorID FROM Quiz";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        ArrayList<Quiz> quizzes = new ArrayList<>();
        while (rs.next()) {
            String creatorUsername = rs.getString("creatorUsername");
            int quizId = rs.getInt("quizId");
            int timesTaken = rs.getInt("timesTaken");
            Quiz quiz = new Quiz(rs.getString("title"), String.valueOf(quizId), timesTaken, creatorUsername);
            quizzes.add(quiz);
        }
        return quizzes;
    }
    public List<QuizAttempt> getUserQuizHistory(String username, int limit) throws SQLException {
        String sql = "SELECT q.quizId, q.title, q.timesTaken, q.creatorUsername, s.score, s.attemptTime " +
                "FROM Score s JOIN Quiz q ON s.quizId = q.quizId " +
                "WHERE s.username = ? ORDER BY s.attemptTime DESC LIMIT ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setInt(2, limit);
        ResultSet rs = stmt.executeQuery();
        List<QuizAttempt> history = new ArrayList<>();
        while (rs.next()) {
            Quiz quiz = new Quiz(
                    rs.getString("title"),
                    String.valueOf(rs.getInt("quizId")),
                    rs.getInt("timesTaken"),
                    rs.getString("creatorUsername")
            );

            int score = rs.getInt("score");
            Timestamp attemptTime = rs.getTimestamp("attemptTime");

            history.add(new QuizAttempt(quiz, score, attemptTime));
        }
        return history;
    }


    public void incrementTimesTaken(int quizId) {
        String sql = "UPDATE Quiz SET timesTaken = timesTaken + 1 WHERE quizId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
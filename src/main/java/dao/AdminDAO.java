package dao;

import models.Announcement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private final Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }
    public void addAnnouncement(String title, String message) throws SQLException {
        String sql = "INSERT INTO Announcements (title, message) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Announcement> getAllAnnouncements() throws SQLException {
        List<Announcement> announcements = new ArrayList<>();
        String sql = "SELECT * FROM Announcements ORDER BY postedAt DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                announcements.add(new Announcement(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getTimestamp("postedAt")
                ));
            }
        }
        return announcements;
    }
    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM Users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    public void clearQuizHistory(int quizId) throws SQLException {
        String sql = "DELETE FROM Score WHERE quizId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            stmt.executeUpdate();
        }
    }
    public void promoteToAdmin(String username) throws SQLException {
        String sql = "UPDATE Users SET isAdmin = TRUE WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
    public int getTotalUserCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    public int getTotalQuizAttempts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Score";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }
    private int getUserId(String username) throws SQLException {
        String query = "SELECT userId FROM Users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId");
            }
        }
        return -1;
    }

    public void removeUser(String username) {
        try {
            int userId = getUserId(username);
            if (userId == -1) return;

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Friends WHERE userId = ? OR friendId = ?")) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Messages WHERE senderId = ? OR recipientId = ?")) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM FriendRequests WHERE senderId = ? OR recipientId = ?")) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Score WHERE userId = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Users WHERE userId = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error removing user: " + username, e);
        }
    }
    public void deleteScoresByQuiz(int quizId) throws SQLException {
        String sql = "DELETE FROM Score WHERE quizId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quizId);
        stmt.executeUpdate();
    }

    public void deleteScoresByUsername(String username) throws SQLException {
        String sql = "DELETE FROM Score WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.executeUpdate();
    }
    public void deleteQuestionsByQuiz(int quizId) throws SQLException {
        String selectQuestionsSql = "SELECT questionId, type FROM Question WHERE quizId = ?";
        PreparedStatement selectStmt = conn.prepareStatement(selectQuestionsSql);
        selectStmt.setInt(1, quizId);
        ResultSet rs = selectStmt.executeQuery();

        while (rs.next()) {
            int questionId = rs.getInt("questionId");
            String type = rs.getString("type");

            switch (type) {
                case "response":
                    executeDelete("DELETE FROM QuestionResponse WHERE questionId = ?", questionId);
                    break;
                case "fill":
                    executeDelete("DELETE FROM FillInBlankQuestion WHERE questionId = ?", questionId);
                    break;
                case "picture":
                    executeDelete("DELETE FROM PictureQuestion WHERE questionId = ?", questionId);
                    break;
                case "mcq":
                    executeDelete("DELETE FROM MultipleChoiceOption WHERE questionId = ?", questionId);
                    executeDelete("DELETE FROM MultipleChoiceQuestion WHERE questionId = ?", questionId);
                    break;
            }
            executeDelete("DELETE FROM Question WHERE questionId = ?", questionId);
        }
    }
    private void executeDelete(String sql, int param) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, param);
        stmt.executeUpdate();
    }
    public void deleteQuiz(int quizId) throws SQLException {
        deleteQuestionsByQuiz(quizId);
        deleteScoresByQuiz(quizId);
        String sql = "DELETE FROM Quiz WHERE quizId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quizId);
        stmt.executeUpdate();
    }
}

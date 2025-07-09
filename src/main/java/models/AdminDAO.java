package models;

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
    public void deleteQuiz(int quizId) throws SQLException {
        String sql = "DELETE FROM Quiz WHERE quizId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
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




}

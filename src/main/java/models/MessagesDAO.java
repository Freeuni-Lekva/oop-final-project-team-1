package models;

import java.sql.*;
import java.util.ArrayList;

public class MessagesDAO {
    private final Connection conn;

    public MessagesDAO(Connection conn) {
        this.conn = conn;
    }

    public void addMessage(Message mess) {
        try {
            int fromId = getUserId(mess.from);
            int toId = getUserId(mess.to);
            if (fromId == -1 || toId == -1) return;

            String sql = "INSERT INTO Messages (senderId, recipientId, message, friendRequest) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, fromId);
                stmt.setInt(2, toId);
                stmt.setString(3, mess.message);
                stmt.setBoolean(4, mess.friendReq);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Message> getMessages(String to) {
        ArrayList<Message> result = new ArrayList<>();
        try {
            int toId = getUserId(to);
            if (toId == -1) return result;

            String sql = """
                SELECT m.message, m.friendRequest, u.username AS sender
                FROM Messages m
                JOIN Users u ON m.senderId = u.userId
                WHERE m.recipientId = ?
                ORDER BY m.sentAt ASC
            """;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, toId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Message m = new Message(to, rs.getString("sender"), rs.getString("message"), rs.getBoolean("friendRequest"));
                    result.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean Compare(String from, String to, String message) {
        try {
            int fromId = getUserId(from);
            int toId = getUserId(to);
            if (fromId == -1 || toId == -1) return false;

            String sql = "SELECT COUNT(*) FROM Messages WHERE senderId = ? AND recipientId = ? AND message = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, fromId);
                stmt.setInt(2, toId);
                stmt.setString(3, message);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeMessage(Message mess) {
        try {
            int fromId = getUserId(mess.from);
            int toId = getUserId(mess.to);
            if (fromId == -1 || toId == -1) return;

            String sql = "DELETE FROM Messages WHERE senderId = ? AND recipientId = ? AND message = ? LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, fromId);
                stmt.setInt(2, toId);
                stmt.setString(3, mess.message);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getUserId(String username) throws SQLException {
        String sql = "SELECT userId FROM Users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId");
            }
        }
        return -1;
    }

    public static class Message {
        public String to;
        public String from;
        public String message;
        public Boolean friendReq;

        public Message(String to, String from, String message, boolean friendReq) {
            this.to = to;
            this.from = from;
            this.message = message;
            this.friendReq = friendReq;
        }
    }
}

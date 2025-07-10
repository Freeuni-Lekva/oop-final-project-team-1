package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountManagerDAO {
    private final Connection conn;

    public AccountManagerDAO(Connection conn) {
        this.conn = conn;
    }
    public boolean checkIfAccountExists(String username) {
            String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                return rs.getInt(1) > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public boolean isCorrectPas(String username, String password){
        String query = "SELECT passwordHash FROM Users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("passwordHash");
                return storedHash.equals(hash(password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public void createAccount(String username, String password){
        if (checkIfAccountExists(username)) {
            System.out.println("Account already exists");
            return;
        }
        String hashedPassword = hash(password);
        String query = "INSERT INTO Users (username, passwordHash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String hash(String password) {
        MessageDigest digest;
        try {
            digest=MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            return hexToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;
            if (val<16) buff.append('0');
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
    public void addFriend(String user1, String user2) throws SQLException {
        int id1 = getUserId(user1);
        int id2 = getUserId(user2);
        if (id1 == -1 || id2 == -1) return;

        String query = "INSERT IGNORE INTO Friends (userId, friendId) VALUES (?, ?), (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id1);
            stmt.setInt(2, id2);
            stmt.setInt(3, id2);
            stmt.setInt(4, id1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeFriend(String user1, String user2) throws SQLException {
        int id1 = getUserId(user1);
        int id2 = getUserId(user2);
        if (id1 == -1 || id2 == -1) return;

        String query = "DELETE FROM Friends WHERE (userId = ? AND friendId = ?) OR (userId = ? AND friendId = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id1);
            stmt.setInt(2, id2);
            stmt.setInt(3, id2);
            stmt.setInt(4, id1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getPeople(String prefix) {
        ArrayList<String> users = new ArrayList<>();
        String query = "SELECT username FROM Users WHERE username LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users.isEmpty() ? null : users;
    }
    public boolean isFriend(String user1, String user2) throws SQLException {
        int id1 = getUserId(user1);
        int id2 = getUserId(user2);
        String query = "SELECT * FROM Friends WHERE userId = ? AND friendId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id1);
            stmt.setInt(2, id2);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    public ArrayList<String> getFriends(String username) throws SQLException {
        ArrayList<String> friends = new ArrayList<>();
        int userId = getUserId(username);
        if (userId == -1) return friends;

        String query = "SELECT u.username FROM Friends f JOIN Users u ON f.friendId = u.userId WHERE f.userId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                friends.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friends;
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
    public boolean isAdmin(String username){

        String query = "SELECT isAdmin FROM Users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isAdmin");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void sendFriendRequest(String senderUsername, String recipientUsername) throws SQLException {
        int senderId = getUserId(senderUsername);
        int recipientId = getUserId(recipientUsername);

        String sql = "INSERT IGNORE INTO FriendRequests (senderId, recipientId) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, recipientId);
            stmt.executeUpdate();
        }
    }
    public boolean hasPendingFriendRequest(String senderUsername, String recipientUsername) throws SQLException {
        int senderId = getUserId(senderUsername);
        int recipientId = getUserId(recipientUsername);

        String sql = "SELECT * FROM FriendRequests WHERE senderId = ? AND recipientId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, recipientId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    public void removeFriendRequest(String senderUsername, String recipientUsername) throws SQLException {
        int senderId = getUserId(senderUsername);
        int recipientId = getUserId(recipientUsername);

        String sql = "DELETE FROM FriendRequests WHERE senderId = ? AND recipientId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, recipientId);
            stmt.executeUpdate();
        }
    }
    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM Users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usernames;
    }

}

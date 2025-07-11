import dao.AccountManagerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountManagerDAOTest {

    @Mock
    private Connection mockConn;
    @Mock
    private PreparedStatement mockStmt;
    @Mock
    private ResultSet mockRs;

    private AccountManagerDAO dao;

    @BeforeEach
    void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);
        dao = spy(new AccountManagerDAO(mockConn));

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
    }

    @Test
    void checkIfAccountExistsTrue() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(3);

        assertTrue(dao.checkIfAccountExists("user"));
        verify(mockStmt).setString(1, "user");
    }

    @Test
    void checkIfAccountExistsFalse() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(0);

        assertFalse(dao.checkIfAccountExists("user"));
    }

    @Test
    void isCorrectPasTrue() throws SQLException {
        String pass = "mypassword";
        String hashed = dao.hash(pass);

        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("passwordHash")).thenReturn(hashed);

        assertTrue(dao.isCorrectPas("user", pass));
    }

    @Test
    void isCorrectPasFalseWrongHash() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getString("passwordHash")).thenReturn("wronghash");

        assertFalse(dao.isCorrectPas("user", "pass"));
    }

    @Test
    void isCorrectPasFalseNoUser() throws SQLException {
        when(mockRs.next()).thenReturn(false);

        assertFalse(dao.isCorrectPas("user", "pass"));
    }

    @Test
    void createAccountInsertsWhenNotExists() throws SQLException {
        doReturn(false).when(dao).checkIfAccountExists("newuser");

        PreparedStatement insertStmt = mock(PreparedStatement.class);
        when(mockConn.prepareStatement(contains("INSERT INTO Users"))).thenReturn(insertStmt);

        dao.createAccount("newuser", "pass");

        verify(insertStmt).setString(eq(1), eq("newuser"));
        verify(insertStmt).setString(eq(2), anyString());
        verify(insertStmt).executeUpdate();
    }

    @Test
    void createAccountNoInsertWhenExists() throws SQLException {
        doReturn(true).when(dao).checkIfAccountExists("existinguser");

        dao.createAccount("existinguser", "pass");

        verify(mockConn, never()).prepareStatement(contains("INSERT INTO Users"));
    }

    @Test
    void getUserIdReturnsId() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("userId")).thenReturn(42);

        int id = dao.getUserId("user");
        assertEquals(42, id);
    }

    @Test
    void getUserIdReturnsMinusOneIfNotFound() throws SQLException {
        when(mockRs.next()).thenReturn(false);

        assertEquals(-1, dao.getUserId("nouser"));
    }

    @Test
    void addFriendExecutesWhenBothExist() throws SQLException {
        doReturn(1).when(dao).getUserId("user1");
        doReturn(2).when(dao).getUserId("user2");

        PreparedStatement insertStmt = mock(PreparedStatement.class);
        when(mockConn.prepareStatement(anyString())).thenReturn(insertStmt);

        dao.addFriend("user1", "user2");

        verify(insertStmt).setInt(1, 1);
        verify(insertStmt).setInt(2, 2);
        verify(insertStmt).setInt(3, 2);
        verify(insertStmt).setInt(4, 1);
        verify(insertStmt).executeUpdate();
    }

    @Test
    void addFriendDoesNothingIfUserMissing() throws SQLException {
        doReturn(-1).when(dao).getUserId("user1");
        doReturn(2).when(dao).getUserId("user2");

        dao.addFriend("user1", "user2");

        verify(mockConn, never()).prepareStatement(anyString());
    }

    @Test
    void removeFriendExecutesWhenBothExist() throws SQLException {
        doReturn(3).when(dao).getUserId("u1");
        doReturn(4).when(dao).getUserId("u2");

        PreparedStatement deleteStmt = mock(PreparedStatement.class);
        when(mockConn.prepareStatement(anyString())).thenReturn(deleteStmt);

        dao.removeFriend("u1", "u2");

        verify(deleteStmt).setInt(1, 3);
        verify(deleteStmt).setInt(2, 4);
        verify(deleteStmt).setInt(3, 4);
        verify(deleteStmt).setInt(4, 3);
        verify(deleteStmt).executeUpdate();
    }

    @Test
    void removeFriendDoesNothingIfUserMissing() throws SQLException {
        doReturn(-1).when(dao).getUserId("u1");
        doReturn(5).when(dao).getUserId("u2");

        dao.removeFriend("u1", "u2");

        verify(mockConn, never()).prepareStatement(anyString());
    }

    @Test
    void getPeopleReturnsNullIfEmpty() throws SQLException {
        when(mockRs.next()).thenReturn(false);

        assertNull(dao.getPeople("abc"));
    }

    @Test
    void getPeopleReturnsListIfNotEmpty() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getString("username")).thenReturn("a", "b");

        ArrayList<String> people = dao.getPeople("a");
        assertEquals(2, people.size());
        assertEquals("a", people.get(0));
        assertEquals("b", people.get(1));
    }

    @Test
    void isFriendTrue() throws SQLException {
        doReturn(1).when(dao).getUserId("u1");
        doReturn(2).when(dao).getUserId("u2");

        when(mockRs.next()).thenReturn(true);

        assertTrue(dao.isFriend("u1", "u2"));
    }

    @Test
    void isFriendFalse() throws SQLException {
        doReturn(1).when(dao).getUserId("u1");
        doReturn(2).when(dao).getUserId("u2");

        when(mockRs.next()).thenReturn(false);

        assertFalse(dao.isFriend("u1", "u2"));
    }

    @Test
    void getFriendsReturnsEmptyIfUserMissing() throws SQLException {
        doReturn(-1).when(dao).getUserId("nope");

        assertTrue(dao.getFriends("nope").isEmpty());
    }

    @Test
    void getFriendsReturnsList() throws SQLException {
        doReturn(1).when(dao).getUserId("user");

        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getString("username")).thenReturn("f1", "f2");

        ArrayList<String> friends = dao.getFriends("user");
        assertEquals(2, friends.size());
        assertEquals("f1", friends.get(0));
        assertEquals("f2", friends.get(1));
    }

    @Test
    void isAdminTrue() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getBoolean("isAdmin")).thenReturn(true);

        assertTrue(dao.isAdmin("admin"));
    }

    @Test
    void isAdminFalse() throws SQLException {
        when(mockRs.next()).thenReturn(false);

        assertFalse(dao.isAdmin("user"));
    }

    @Test
    void sendFriendRequestExecutes() throws SQLException {
        doReturn(1).when(dao).getUserId("sender");
        doReturn(2).when(dao).getUserId("recipient");

        PreparedStatement insertStmt = mock(PreparedStatement.class);
        when(mockConn.prepareStatement(anyString())).thenReturn(insertStmt);

        dao.sendFriendRequest("sender", "recipient");

        verify(insertStmt).setInt(1, 1);
        verify(insertStmt).setInt(2, 2);
        verify(insertStmt).executeUpdate();
    }

    @Test
    void hasPendingFriendRequestTrue() throws SQLException {
        doReturn(1).when(dao).getUserId("s");
        doReturn(2).when(dao).getUserId("r");

        when(mockRs.next()).thenReturn(true);

        assertTrue(dao.hasPendingFriendRequest("s", "r"));
    }

    @Test
    void hasPendingFriendRequestFalse() throws SQLException {
        doReturn(1).when(dao).getUserId("s");
        doReturn(2).when(dao).getUserId("r");

        when(mockRs.next()).thenReturn(false);

        assertFalse(dao.hasPendingFriendRequest("s", "r"));
    }

    @Test
    void removeFriendRequestExecutes() throws SQLException {
        doReturn(1).when(dao).getUserId("s");
        doReturn(2).when(dao).getUserId("r");

        PreparedStatement deleteStmt = mock(PreparedStatement.class);
        when(mockConn.prepareStatement(anyString())).thenReturn(deleteStmt);

        dao.removeFriendRequest("s", "r");

        verify(deleteStmt).setInt(1, 1);
        verify(deleteStmt).setInt(2, 2);
        verify(deleteStmt).executeUpdate();
    }

    @Test
    void getAllUsernamesReturnsList() throws SQLException {
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getString("username")).thenReturn("u1", "u2");

        ArrayList<String> names = dao.getAllUsernames();

        assertEquals(2, names.size());
        assertEquals("u1", names.get(0));
        assertEquals("u2", names.get(1));
    }
}

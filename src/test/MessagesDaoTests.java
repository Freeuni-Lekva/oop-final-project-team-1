import dao.MessagesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MessagesDaoTests {

    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private MessagesDAO dao;

    @BeforeEach
    void setup() throws SQLException {
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);
        dao = new MessagesDAO(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
    }

    @Test
    void addMessageInsertsCorrectly() throws SQLException {
        MessagesDAO.Message msg = new MessagesDAO.Message("toUser", "fromUser", "hello", false);
        MessagesDAO spyDao = spy(dao);
        doReturn(1).when(spyDao).getUserId("fromUser");
        doReturn(2).when(spyDao).getUserId("toUser");
        spyDao.addMessage(msg);
        verify(mockStmt).setInt(1, 1);
        verify(mockStmt).setInt(2, 2);
        verify(mockStmt).setString(3, "hello");
        verify(mockStmt).setBoolean(4, false);
        verify(mockStmt).executeUpdate();
    }

    @Test
    void addMessageDoesNothingIfUserNotFound() throws SQLException {
        MessagesDAO.Message msg = new MessagesDAO.Message("toUser", "fromUser", "hello", false);
        MessagesDAO spyDao = spy(dao);
        doReturn(-1).when(spyDao).getUserId("fromUser");
        doReturn(-1).when(spyDao).getUserId("toUser");
        spyDao.addMessage(msg);
        verify(mockConn, never()).prepareStatement(anyString());
    }

    @Test
    void getMessagesReturnsEmptyIfUserNotFound() throws SQLException {
        MessagesDAO spyDao = spy(dao);
        doReturn(-1).when(spyDao).getUserId("toUser");
        ArrayList<MessagesDAO.Message> msgs = spyDao.getMessages("toUser");
        assertTrue(msgs.isEmpty());
    }

    @Test
    void getMessagesReturnsMessages() throws SQLException {
        MessagesDAO spyDao = spy(dao);
        doReturn(2).when(spyDao).getUserId("toUser");
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getString("sender")).thenReturn("fromUser1", "fromUser2");
        when(mockRs.getString("message")).thenReturn("msg1", "msg2");
        when(mockRs.getBoolean("friendRequest")).thenReturn(false, true);
        ArrayList<MessagesDAO.Message> msgs = spyDao.getMessages("toUser");
        assertEquals(2, msgs.size());
        assertEquals("toUser", msgs.get(0).to);
        assertEquals("fromUser1", msgs.get(0).from);
        assertEquals("msg1", msgs.get(0).message);
        assertFalse(msgs.get(0).friendReq);
        assertEquals("toUser", msgs.get(1).to);
        assertEquals("fromUser2", msgs.get(1).from);
        assertEquals("msg2", msgs.get(1).message);
        assertTrue(msgs.get(1).friendReq);
    }

    @Test
    void compareReturnsFalseIfUserNotFound() throws SQLException {
        MessagesDAO spyDao = spy(dao);
        doReturn(-1).when(spyDao).getUserId("fromUser");
        assertFalse(spyDao.Compare("fromUser", "toUser", "msg"));
    }

    @Test
    void compareReturnsTrueWhenCountMoreThanZero() throws SQLException {
        MessagesDAO spyDao = spy(dao);
        doReturn(1).when(spyDao).getUserId("fromUser");
        doReturn(2).when(spyDao).getUserId("toUser");
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(5);
        assertTrue(spyDao.Compare("fromUser", "toUser", "msg"));
    }

    @Test
    void compareReturnsFalseWhenCountIsZero() throws SQLException {
        MessagesDAO spyDao = spy(dao);
        doReturn(1).when(spyDao).getUserId("fromUser");
        doReturn(2).when(spyDao).getUserId("toUser");
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(0);
        assertFalse(spyDao.Compare("fromUser", "toUser", "msg"));
    }

    @Test
    void removeMessageDeletesCorrectly() throws SQLException {
        MessagesDAO.Message msg = new MessagesDAO.Message("toUser", "fromUser", "hello", false);
        MessagesDAO spyDao = spy(dao);
        doReturn(1).when(spyDao).getUserId("fromUser");
        doReturn(2).when(spyDao).getUserId("toUser");
        spyDao.removeMessage(msg);
        verify(mockStmt).setInt(1, 1);
        verify(mockStmt).setInt(2, 2);
        verify(mockStmt).setString(3, "hello");
        verify(mockStmt).executeUpdate();
    }
}

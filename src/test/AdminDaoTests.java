import dao.AdminDAO;
import models.Announcement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminDaoTests {

    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private AdminDAO dao;

    @BeforeEach
    void setup() throws SQLException {
        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);
        dao = new AdminDAO(mockConn);
    }

    @Test
    void addAnnouncementInsertsCorrectly() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        dao.addAnnouncement("Title", "Message");
        verify(mockStmt).setString(1, "Title");
        verify(mockStmt).setString(2, "Message");
        verify(mockStmt).executeUpdate();
    }

    @Test
    void getAllAnnouncementsReturnsList() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("id")).thenReturn(1);
        when(mockRs.getString("title")).thenReturn("Title");
        when(mockRs.getString("message")).thenReturn("Message");
        when(mockRs.getTimestamp("postedAt")).thenReturn(new Timestamp(System.currentTimeMillis()));

        List<Announcement> announcements = dao.getAllAnnouncements();
        assertEquals(1, announcements.size());
        assertEquals("Title", announcements.get(0).getTitle());
    }

    @Test
    void deleteUserDeletesCorrectUser() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        dao.deleteUser("john");
        verify(mockStmt).setString(1, "john");
        verify(mockStmt).executeUpdate();
    }

    @Test
    void clearQuizHistoryDeletesScore() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        dao.clearQuizHistory(42);
        verify(mockStmt).setInt(1, 42);
        verify(mockStmt).executeUpdate();
    }

    @Test
    void promoteToAdminUpdatesFlag() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        dao.promoteToAdmin("admin");
        verify(mockStmt).setString(1, "admin");
        verify(mockStmt).executeUpdate();
    }

    @Test
    void getTotalUserCountReturnsCorrectNumber() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(10);
        assertEquals(10, dao.getTotalUserCount());
    }

    @Test
    void getTotalQuizAttemptsReturnsCorrectNumber() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(25);
        assertEquals(25, dao.getTotalQuizAttempts());
    }

    @Test
    void removeUserDeletesCorrectly() throws SQLException {
        AdminDAO spyDao = spy(dao);
        doReturn(3).when(spyDao).getUserId("john");
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        spyDao.removeUser("john");

        verify(mockStmt, atLeastOnce()).setInt(anyInt(), eq(3));
        verify(mockStmt, atLeastOnce()).executeUpdate();
    }

    @Test
    void deleteScoresByQuizDeletesCorrectly() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        dao.deleteScoresByQuiz(7);
        verify(mockStmt).setInt(1, 7);
        verify(mockStmt).executeUpdate();
    }

    @Test
    void deleteScoresByUsernameDeletesCorrectly() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        dao.deleteScoresByUsername("john");
        verify(mockStmt).setString(1, "john");
        verify(mockStmt).executeUpdate();
    }

    @Test
    void deleteQuizCallsSubMethods() throws SQLException {
        AdminDAO spyDao = spy(dao);
        doNothing().when(spyDao).deleteQuestionsByQuiz(9);
        doNothing().when(spyDao).deleteScoresByQuiz(9);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        spyDao.deleteQuiz(9);
        verify(spyDao).deleteQuestionsByQuiz(9);
        verify(spyDao).deleteScoresByQuiz(9);
        verify(mockStmt).setInt(1, 9);
        verify(mockStmt).executeUpdate();
    }

    @Test
    void deleteQuestionsByQuizExecutesAllDeletes() throws SQLException {
        AdminDAO spyDao = spy(dao);
        when(mockConn.prepareStatement(contains("SELECT"))).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("questionId")).thenReturn(1, 2);
        when(mockRs.getString("type")).thenReturn("response", "fill");
        doNothing().when(spyDao).executeDelete(anyString(), anyInt());
        spyDao.deleteQuestionsByQuiz(5);
        verify(spyDao, times(4)).executeDelete(anyString(), anyInt());
        verify(spyDao).executeDelete("DELETE FROM QuestionResponse WHERE questionId = ?", 1);
        verify(spyDao).executeDelete("DELETE FROM FillInBlankQuestion WHERE questionId = ?", 2);
        verify(spyDao).executeDelete("DELETE FROM Question WHERE questionId = ?", 1);
        verify(spyDao).executeDelete("DELETE FROM Question WHERE questionId = ?", 2);
    }
    @Test
    void addAnnouncementThrowsRuntimeExceptionOnSQLException() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));
        AdminDAO dao = new AdminDAO(mockConn);
        assertThrows(RuntimeException.class, () -> dao.addAnnouncement("T", "M"));
    }
    @Test
    void removeUserThrowsRuntimeExceptionOnSQLException() throws SQLException {
        AdminDAO spyDao = spy(new AdminDAO(mockConn));
        doReturn(3).when(spyDao).getUserId("badUser");
        when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException("Error"));
        assertThrows(RuntimeException.class, () -> spyDao.removeUser("badUser"));
    }
    @Test
    void deleteQuestionsByQuizHandlesPictureType() throws Exception {
        AdminDAO spyDao = spy(dao);
        when(mockConn.prepareStatement(contains("SELECT"))).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("questionId")).thenReturn(10);
        when(mockRs.getString("type")).thenReturn("picture");

        doNothing().when(spyDao).executeDelete(anyString(), anyInt());

        spyDao.deleteQuestionsByQuiz(99);

        verify(spyDao).executeDelete("DELETE FROM PictureQuestion WHERE questionId = ?", 10);
        verify(spyDao).executeDelete("DELETE FROM Question WHERE questionId = ?", 10);
    }
    @Test
    void deleteQuestionsByQuizHandlesMcqType() throws Exception {
        AdminDAO spyDao = spy(dao);
        when(mockConn.prepareStatement(contains("SELECT"))).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, false);
        when(mockRs.getInt("questionId")).thenReturn(11);
        when(mockRs.getString("type")).thenReturn("mcq");

        doNothing().when(spyDao).executeDelete(anyString(), anyInt());

        spyDao.deleteQuestionsByQuiz(77);

        verify(spyDao).executeDelete("DELETE FROM MultipleChoiceOption WHERE questionId = ?", 11);
        verify(spyDao).executeDelete("DELETE FROM MultipleChoiceQuestion WHERE questionId = ?", 11);
        verify(spyDao).executeDelete("DELETE FROM Question WHERE questionId = ?", 11);
    }
}

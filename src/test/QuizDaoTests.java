import dao.QuizDAO;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizDaoTest {

    @Mock Connection mockConnection;
    @Mock PreparedStatement mockStatement;
    @Mock ResultSet mockResultSet;

    QuizDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        dao = new QuizDAO(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void insertResponseQuestionExecutes() throws Exception {
        QuizDAO spyDao = spy(dao);
        doReturn(5).when(spyDao).insertQuestion(anyInt(), anyString(), anyString());

        spyDao.insertResponseQuestion(1, "Q text", "Answer");

        verify(mockConnection).prepareStatement("INSERT INTO QuestionResponse (questionId, Answer) VALUES (?, ?)");
        verify(mockStatement).setInt(1, 5);
        verify(mockStatement).setString(2, "Answer");
        verify(mockStatement).executeUpdate();
    }

    @Test
    void insertFillInBlankQuestionExecutes() throws Exception {
        QuizDAO spyDao = spy(dao);
        doReturn(10).when(spyDao).insertQuestion(anyInt(), anyString(), anyString());

        spyDao.insertFillInBlankQuestion(1, "Q text", "Answer");

        verify(mockConnection).prepareStatement("INSERT INTO FillInBlankQuestion (questionId, Answer) VALUES (?, ?)");
        verify(mockStatement).setInt(1, 10);
        verify(mockStatement).setString(2, "Answer");
        verify(mockStatement).executeUpdate();
    }

    @Test
    void insertPictureQuestionExecutes() throws Exception {
        QuizDAO spyDao = spy(dao);
        doReturn(20).when(spyDao).insertQuestion(anyInt(), anyString(), anyString());

        spyDao.insertPictureQuestion(1, "Q text", "image.png", "Answer");

        verify(mockConnection).prepareStatement("INSERT INTO PictureQuestion (questionId, imageUrl, Answer) VALUES (?, ?, ?)");
        verify(mockStatement).setInt(1, 20);
        verify(mockStatement).setString(2, "image.png");
        verify(mockStatement).setString(3, "Answer");
        verify(mockStatement).executeUpdate();
    }

    @Test
    void insertMcqOptionExecutes() throws Exception {
        dao.insertMCQOption(5, "Option text", true);

        verify(mockConnection).prepareStatement("INSERT INTO MultipleChoiceOption (questionId, optionText, isCorrect) VALUES (?, ?, ?)");
        verify(mockStatement).setInt(1, 5);
        verify(mockStatement).setString(2, "Option text");
        verify(mockStatement).setBoolean(3, true);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void insertMultipleChoiceMetaExecutes() throws Exception {
        dao.insertMultipleChoiceMeta(7);

        verify(mockConnection).prepareStatement("INSERT INTO MultipleChoiceQuestion (questionId) VALUES (?)");
        verify(mockStatement).setInt(1, 7);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void insertMultipleChoiceQuestionExecutes() throws Exception {
        QuizDAO spyDao = spy(dao);
        doReturn(11).when(spyDao).insertQuestion(anyInt(), eq("mcq"), anyString());
        doNothing().when(spyDao).insertMultipleChoiceMeta(anyInt());
        doNothing().when(spyDao).insertMCQOption(anyInt(), anyString(), anyBoolean());

        List<Option> options = Arrays.asList(
                new Option(0, "opt1", false),
                new Option(0, "opt2", true)
        );

        spyDao.insertMultipleChoiceQuestion(1, "Question?", options);

        verify(spyDao).insertMultipleChoiceMeta(11);
        verify(spyDao, times(2)).insertMCQOption(eq(11), anyString(), anyBoolean());
    }

    @Test
    void getRecentlyTakenQuizzesReturnsList() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("title")).thenReturn("Quiz1", "Quiz2");
        when(mockResultSet.getString("quizID")).thenReturn("1", "2");
        when(mockResultSet.getInt("timesTaken")).thenReturn(5, 3);
        when(mockResultSet.getString("creatorUsername")).thenReturn("creatorA", "creatorB");

        ArrayList<Quiz> quizzes = dao.getRecentlyTakenQuizzes("user");
        assertEquals(2, quizzes.size());
        assertEquals("Quiz1", quizzes.get(0).getTitle());
    }

    @Test
    void getRecentlyCreatedQuizzesReturnsSublist() throws Exception {
        ArrayList<Quiz> fullList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            fullList.add(new Quiz("Quiz" + i, String.valueOf(i), i, "creator" + i));
        }
        QuizDAO spyDao = spy(dao);
        doReturn(fullList).when(spyDao).getQuizList();

        ArrayList<Quiz> recent = spyDao.getRecentlyCreatedQuizzes();
        assertEquals(5, recent.size());
        assertEquals("Quiz2", recent.get(0).getTitle());
    }

    @Test
    void getPopularQuizzesReturnsSortedTop() throws Exception {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz("QuizA", "1", 10, "c1"));
        quizzes.add(new Quiz("QuizB", "2", 20, "c2"));
        quizzes.add(new Quiz("QuizC", "3", 5, "c3"));
        QuizDAO spyDao = spy(dao);
        doReturn(quizzes).when(spyDao).getQuizList();

        ArrayList<Quiz> popular = spyDao.getPopularQuizzes();
        assertEquals(3, popular.size());
        assertEquals("QuizB", popular.get(0).getTitle());
    }

    @Test
    void getQuizListReturnsAll() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("title")).thenReturn("Quiz1", "Quiz2");
        when(mockResultSet.getString("quizID")).thenReturn("1", "2");
        when(mockResultSet.getInt("timesTaken")).thenReturn(10, 20);
        when(mockResultSet.getString("creatorUsername")).thenReturn("creator1", "creator2");

        ArrayList<Quiz> quizzes = dao.getQuizList();
        assertEquals(2, quizzes.size());
        assertEquals("Quiz1", quizzes.get(0).getTitle());
    }

    @Test
    void isRandomReturnsBoolean() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBoolean("randomQuiz")).thenReturn(true);

        assertTrue(dao.isRandom(1));
    }

    @Test
    void isRandomThrowsIfNotFound() {
        try {
            when(mockResultSet.next()).thenReturn(false);
            dao.isRandom(999);
            fail("Expected RuntimeException");
        } catch (RuntimeException | SQLException e) {
            assertTrue(e.getMessage().contains("Quiz id not found"));
        }
    }

    @Test
    void getQuestionsForQuizReturnsQuestions() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, true, true, false);
        when(mockResultSet.getInt("questionId")).thenReturn(1, 2, 3, 4);
        when(mockResultSet.getString("text")).thenReturn("Text1", "Text2", "Text3", "Text4");
        when(mockResultSet.getString("type")).thenReturn("response", "fill", "picture", "mcq");

        QuizDAO spyDao = spy(dao);
        doReturn(new QuestionResponse(1, "Text1", "answer1")).when(spyDao).loadQuestionResponse(1, "Text1");
        doReturn(new FillInBlank(2, "Text2", "answer2")).when(spyDao).loadFillInBlank(2, "Text2");
        doReturn(new PictureResponse(3, "Text3", "url", "answer3")).when(spyDao).loadPictureResponse(3, "Text3");
        doReturn(new MultipleChoice(4, "Text4", new ArrayList<>())).when(spyDao).loadMultipleChoice(4, "Text4");

        List<Questions> questions = spyDao.getQuestionsForQuiz(1);
        assertEquals(4, questions.size());
    }

    @Test
    void loadQuestionResponseReturnsCorrect() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Answer")).thenReturn("correctAnswer");

        QuestionResponse qr = dao.loadQuestionResponse(1, "Question?");
        assertEquals("correctAnswer", qr.getCorrectAnswer());
    }

    @Test
    void loadFillInBlankReturnsCorrect() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Answer")).thenReturn("fillAnswer");

        FillInBlank fib = dao.loadFillInBlank(1, "Question?");
        assertEquals("fillAnswer", fib.getCorrectAnswer());
    }

    @Test
    void loadPictureResponseReturnsCorrect() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("imageUrl")).thenReturn("image.jpg");
        when(mockResultSet.getString("Answer")).thenReturn("picAnswer");

        PictureResponse pr = dao.loadPictureResponse(1, "Question?");
        assertEquals("image.jpg", pr.getImage());
        assertEquals("picAnswer", pr.getCorrectAnswer());
    }

    @Test
    void loadMultipleChoiceReturnsCorrect() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("optionId")).thenReturn(1, 2);
        when(mockResultSet.getString("optionText")).thenReturn("opt1", "opt2");
        when(mockResultSet.getBoolean("isCorrect")).thenReturn(true, false);

        MultipleChoice mcq = dao.loadMultipleChoice(1, "Question?");
        assertEquals(2, mcq.getOptions().size());
    }

    @Test
    void insertScoreExecutes() throws Exception {
        QuizDAO spyDao = spy(dao);
        doReturn(33).when(spyDao).getUserIdByUsername("user1");

        spyDao.insertScore(1, "user1", 100);

        verify(mockConnection).prepareStatement("INSERT INTO Score (quizId, username, userId, score) VALUES (?, ?, ?, ?)");
        verify(mockStatement).setInt(1, 1);
        verify(mockStatement).setString(2, "user1");
        verify(mockStatement).setInt(3, 33);
        verify(mockStatement).setInt(4, 100);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void getTopScorersForQuizReturnsList() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("username")).thenReturn("userA", "userB");
        when(mockResultSet.getInt("score")).thenReturn(99, 88);

        List<Map<String, Object>> top = dao.getTopScorersForQuiz(1);
        assertEquals(2, top.size());
        assertEquals("userA", top.get(0).get("username"));
    }

    @Test
    void getHighestScoreForUserReturnsMax() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("maxScore")).thenReturn(77);
        int max = dao.getHighestScoreForUser(1, "user");
        assertEquals(77, max);
    }

    @Test
    void getHighestScoreForUserReturnsZeroIfNone() throws Exception {
        when(mockResultSet.next()).thenReturn(false);
        int max = dao.getHighestScoreForUser(1, "user");
        assertEquals(0, max);
    }

    @Test
    void getUserIdByUsernameReturnsId() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("userId")).thenReturn(42);

        int id = dao.getUserIdByUsername("user");
        assertEquals(42, id);
    }

    @Test
    void getUserIdByUsernameThrowsException() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        assertThrows(SQLException.class, () -> dao.getUserIdByUsername("missing"));
    }

    @Test
    void getAllQuizzesReturnsList() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("creatorUsername")).thenReturn("creator1", "creator2");
        when(mockResultSet.getInt("quizId")).thenReturn(1, 2);
        when(mockResultSet.getInt("timesTaken")).thenReturn(10, 20);
        when(mockResultSet.getString("title")).thenReturn("Quiz1", "Quiz2");

        ArrayList<Quiz> quizzes = dao.getAllQuizzes();
        assertEquals(2, quizzes.size());
        assertEquals("Quiz1", quizzes.get(0).getTitle());
    }

    @Test
    void getUserQuizHistoryReturnsList() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("title")).thenReturn("Quiz1", "Quiz2");
        when(mockResultSet.getInt("quizId")).thenReturn(100, 101);
        when(mockResultSet.getInt("timesTaken")).thenReturn(5, 6);
        when(mockResultSet.getString("creatorUsername")).thenReturn("creator1", "creator2");
        when(mockResultSet.getInt("score")).thenReturn(88, 77);
        when(mockResultSet.getTimestamp("attemptTime")).thenReturn(new Timestamp(1000), new Timestamp(2000));

        List<QuizAttempt> history = dao.getUserQuizHistory("user", 2);
        assertEquals(2, history.size());
        assertEquals("Quiz1", history.get(0).getQuiz().getTitle());
    }

    @Test
    void incrementTimesTakenShouldExecuteUpdateSuccessfully() throws Exception {
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStmt);

        dao.incrementTimesTaken(42);

        verify(mockConnection).prepareStatement(startsWith("UPDATE Quiz SET timesTaken"));
        verify(mockStmt).setInt(1, 42);
        verify(mockStmt).executeUpdate();
    }
}

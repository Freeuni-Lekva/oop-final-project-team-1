<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.MessagesDAO" %>
<%@ page import="dao.QuizDAO" %>
<%@ page import="models.QuizAttempt" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%
    String curr = (String) session.getAttribute("userName");
    AccountManagerDAO acc = (AccountManagerDAO) application.getAttribute("accountManager");
    String currentUser = (String) session.getAttribute("userName");
    MessagesDAO ms = (MessagesDAO) application.getAttribute("messages");
    ArrayList<MessagesDAO.Message> messages = ms.getMessages(currentUser);

    QuizDAO quizDAO = (QuizDAO) application.getAttribute("quizDAO"); // Make sure quizDAO is initialized in context
    List<QuizAttempt> quizHistory = null;
    if (currentUser != null) {
        try {
            quizHistory = quizDAO.getUserQuizHistory(currentUser, 3); // last 3 quizzes
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


%>

<html>
<head>
  <title>HomePage</title>
</head>
 <h1>User:  <%=(String) session.getAttribute("userName")%>  </h1>
<div style="display: flex; gap: 10px;">
    <a href="Quizzes">
    <button>Quizzes</button>
    </a>
    <a href="createQuiz.jsp">
    <button>Create a Quiz</button>
    </a>
    <a href="index.jsp">
        <button>Logout</button>
    </a>
    <a href="FriendList.jsp">
        <button>Friends</button>
    </a>
    <%
        if(acc.isAdmin(curr)){
    %>
    <a href="AdminControlPanelServlet">
        <button>Admin Control Panel</button>
    </a>
    <%
        }
    %>
</div>
<%
    session.setAttribute("User", request.getParameter("name") );
%>
<form action="filteredUsers.jsp" method="get">
    <input type="search" name="query" placeholder="Search..." />
    <button type="submit">Search</button>
</form>

<%

    if (messages.isEmpty()) {
%>
<p>No messages</p>
<%
} else {
    for(MessagesDAO.Message temp : messages) {
        String type="message";
        if(temp.friendReq)type="Friend Request";

%>
<div>
    <a href="message.jsp?messageType=<%=type%>&from=<%=temp.from%>&message=<%=temp.message%>"><%=temp.from + " Sent you a " + type %>
    </a><br>
</div>
<%

        }

    }


%>
<h3>Your Recent Quiz History</h3>
<%
    if (quizHistory == null || quizHistory.isEmpty()) {
%>
<p>You haven't taken any quizzes yet.</p>
<%
} else {
%>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Quiz Title</th>
        <th>Score</th>
    </tr>
    <%
        for (QuizAttempt attempt : quizHistory) {
            models.Quiz quiz = attempt.getQuiz();
    %>
    <tr>
        <td><%= quiz.getTitle() %></td>
        <td><%= attempt.getScore() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    }
%>

<form action="HistoryServlet" method="get" style="display:inline;">
    <button type="submit">View Full Quiz History</button>
</form>

<body>
<br/>
</body>
</html>
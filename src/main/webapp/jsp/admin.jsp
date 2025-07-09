<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="dao.QuizDAO" %>
<%@ page import="dao.AdminDAO" %>
<%@ page import="dao.MessagesDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Quiz" %><%--
  Created by IntelliJ IDEA.
  User: ggulo
  Date: 7/8/2025
  Time: 8:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    AccountManagerDAO accManager = (AccountManagerDAO) request.getAttribute("accountManager");
    QuizDAO quiz = (QuizDAO) request.getAttribute("quizDAO");
    AdminDAO admin = (AdminDAO) request.getAttribute("admin");
    MessagesDAO messages = (MessagesDAO) request.getAttribute("messages");
     ArrayList<String> users= accManager.getAllUsernames();
    ArrayList<Quiz> quizzes = quiz.getAllQuizzes();

%>
<html>
<head>
    <title>Administrator Panel</title>
</head>
<body>
<h1>Administrator Control Panel</h1>
<a href="HomePage.jsp"><button>Home Page</button></a>
<h2>Manage Users</h2>
<% for (String username : users) {
    if (!accManager.isAdmin(username)) {
%>
<div style="margin-bottom: 8px;">
    <b><%= username %></b>
    <form action="promoteToAdminServlet" method="post" style="display:inline;">
        <input type="hidden" name="username" value="<%= username %>">
        <button type="submit">Make Admin</button>
    </form>
    <form action="RemoveUserServlet" method="post" style="display:inline;">
        <input type="hidden" name="username" value="<%= username %>">
        <button type="submit">Remove User</button>
    </form>
</div>
<%  }
}
%>

<hr>

<h2>All Quizzes</h2>
<% for (Quiz q : quizzes) { %>
<div style="margin-bottom: 10px;">
        <span>
            <b>Title:</b> <%= q.getTitle() %> |
            <b>Creator:</b> <%= q.getCreator() %> |
            <b>Times Taken:</b> <%= q.getTimesTaken() %>
        </span>
    <form action="RemoveQuizServlet" method="post" style="display:inline;">
        <input type="hidden" name="quizId" value="<%= q.getQuizID() %>">
        <button type="submit">Remove Quiz</button>
    </form>
    <form action="RemoveQuizHistoryServlet" method="post" style="display:inline;">
        <input type="hidden" name="quizId" value="<%= q.getQuizID() %>">
        <button type="submit">Remove Quiz History</button>
    </form>
</div>
<% } %>

</body>
</html>
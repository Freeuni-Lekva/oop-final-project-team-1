<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="dao.QuizDAO" %>
<%@ page import="dao.AdminDAO" %>
<%@ page import="dao.MessagesDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    AccountManagerDAO accManager = (AccountManagerDAO) request.getAttribute("accountManager");
    QuizDAO quiz = (QuizDAO) request.getAttribute("quizDAO");
    AdminDAO admin = (AdminDAO) request.getAttribute("admin");
    MessagesDAO messages = (MessagesDAO) request.getAttribute("messages");
    ArrayList<String> users = accManager.getAllUsernames();
    ArrayList<Quiz> quizzes = quiz.getAllQuizzes();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Administrator Panel</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
            background: #f4f7fc;
        }
        h1, h2 {
            color: #333;
        }
        .button {
            background-color: #3366cc;
            color: white;
            border: none;
            padding: 8px 14px;
            margin-left: 6px;
            border-radius: 5px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #224d99;
        }
        .user-item, .quiz-item {
            background: white;
            padding: 12px 15px;
            margin-bottom: 10px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgb(0 0 0 / 0.1);
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        form {
            margin: 0;
        }
        .announcement-form {
            background: white;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgb(0 0 0 / 0.1);
        }
        .announcement-form input[type="text"],
        .announcement-form textarea {
            width: 100%;
            padding: 8px 10px;
            margin: 6px 0 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
            resize: vertical;
        }
    </style>
</head>
<body>

<h1>Administrator Control Panel</h1>
<a href="HomePage.jsp"><button class="button">Home Page</button></a>

<hr>

<div class="announcement-form">
    <h2>Create Announcement</h2>
    <form action="AddAnnouncementServlet" method="post">
        <label for="title">Title:</label><br/>
        <input type="text" id="title" name="title" required maxlength="100"/><br/>

        <label for="message">Message:</label><br/>
        <textarea id="message" name="message" rows="4" required maxlength="500"></textarea><br/>

        <button type="submit" class="button">Post Announcement</button>
    </form>
</div>

<hr>

<h2>Manage Users</h2>
<% for (String username : users) {
    if (!accManager.isAdmin(username)) { %>
<div class="user-item">
    <div><b><%= username %></b></div>
    <div>
        <form action="promoteToAdminServlet" method="post" style="display:inline;">
            <input type="hidden" name="username" value="<%= username %>"/>
            <button type="submit" class="button">Make Admin</button>
        </form>
        <form action="RemoveUserServlet" method="post" style="display:inline;">
            <input type="hidden" name="username" value="<%= username %>"/>
            <button type="submit" class="button">Remove User</button>
        </form>
    </div>
</div>
<% }
} %>

<hr>

<h2>All Quizzes</h2>
<% for (Quiz q : quizzes) { %>
<div class="quiz-item">
    <div>
        <b>Title:</b> <%= q.getTitle() %> |
        <b>Creator:</b> <%= q.getCreator() %> |
        <b>Times Taken:</b> <%= q.getTimesTaken() %>
    </div>
    <div>
        <form action="RemoveQuizServlet" method="post" style="display:inline;">
            <input type="hidden" name="quizId" value="<%= q.getQuizID() %>"/>
            <button type="submit" class="button">Remove Quiz</button>
        </form>
        <form action="RemoveQuizHistoryServlet" method="post" style="display:inline;">
            <input type="hidden" name="quizId" value="<%= q.getQuizID() %>"/>
            <button type="submit" class="button">Remove Quiz History</button>
        </form>
    </div>
</div>
<% } %>

</body>
</html>

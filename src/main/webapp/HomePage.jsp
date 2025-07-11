<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.MessagesDAO" %>
<%@ page import="dao.QuizDAO" %>
<%@ page import="models.QuizAttempt" %>
<%@ page import="models.Announcement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="dao.AdminDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>HomePage</title>
    <style>

        * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #89f7fe, #66a6ff);
            color: #333;
            min-height: 100vh;
            padding: 30px;
        }
        h1 {
            margin-bottom: 20px;
            color: #222;
            font-size: 2rem;
        }

        .nav-buttons {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
            margin-bottom: 30px;
        }
        .nav-buttons a button {
            background: #66a6ff;
            border: none;
            color: white;
            padding: 10px 18px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        .nav-buttons a button:hover {
            background: #4a8fdc;
        }

        form.search-form {
            margin-bottom: 30px;
            display: flex;
            gap: 8px;
            max-width: 350px;
        }
        form.search-form input[type="search"] {
            flex: 1;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.3s;
        }
        form.search-form input[type="search"]:focus {
            border-color: #66a6ff;
            outline: none;
            box-shadow: 0 0 5px rgba(102, 166, 255, 0.5);
        }
        form.search-form button {
            padding: 10px 18px;
            border: none;
            background: #66a6ff;
            color: white;
            border-radius: 8px;
            cursor: pointer;
            font-weight: 600;
            transition: background 0.3s;
        }
        form.search-form button:hover {
            background: #4a8fdc;
        }

        .messages {
            margin-bottom: 40px;
        }
        .messages p {
            font-style: italic;
            color: #555;
        }
        .message-item {
            background: white;
            padding: 12px 15px;
            margin-bottom: 8px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            transition: background 0.2s;
        }
        .message-item a {
            color: #007BFF;
            text-decoration: none;
            font-weight: 600;
        }
        .message-item a:hover {
            text-decoration: underline;
            color: #0056b3;
        }

        h3 {
            margin-bottom: 15px;
            color: #222;
            font-weight: 700;
        }
        table {
            width: 100%;
            max-width: 600px;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px 20px;
            text-align: left;
            border-bottom: 1px solid #eee;
            font-size: 1rem;
        }
        th {
            background: #66a6ff;
            color: white;
            font-weight: 600;
        }
        tr:last-child td {
            border-bottom: none;
        }

        form.full-history {
            max-width: 600px;
            margin-bottom: 50px;
        }
        form.full-history button {
            background: #66a6ff;
            border: none;
            color: white;
            padding: 12px 25px;
            border-radius: 8px;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.3s ease;
            font-weight: 600;
        }
        form.full-history button:hover {
            background: #4a8fdc;
        }


        .announcements-section {
            background: white;
            padding: 20px;
            max-width: 700px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            margin-bottom: 40px;
        }
        .announcement-item {
            border-bottom: 1px solid #eee;
            padding: 12px 0;
        }
        .announcement-item:last-child {
            border-bottom: none;
        }
        .announcement-title {
            font-weight: 700;
            font-size: 1.2rem;
            color: #3366cc;
            margin-bottom: 6px;
        }
        .announcement-message {
            font-size: 1rem;
            margin-bottom: 6px;
            color: #444;
        }
        .announcement-date {
            font-size: 0.85rem;
            color: #777;
            font-style: italic;
        }
    </style>
</head>
<body>

<%
    String curr = (String) session.getAttribute("userName");
    AccountManagerDAO acc = (AccountManagerDAO) application.getAttribute("accountManager");
    String currentUser = (String) session.getAttribute("userName");
    MessagesDAO ms = (MessagesDAO) application.getAttribute("messages");
    ArrayList<MessagesDAO.Message> messages = ms.getMessages(currentUser);
    AdminDAO admin = (AdminDAO) application.getAttribute("admin");
    QuizDAO quizDAO = (QuizDAO) application.getAttribute("quizDAO"); // Make sure quizDAO is initialized in context
    List<QuizAttempt> quizHistory = null;
    // Get announcements list from request attribute "announcements"
    List<Announcement> announcements = null;
    if (currentUser != null) {
        try {
            quizHistory = quizDAO.getUserQuizHistory(currentUser, 3); // last 3 quizzes
            announcements = admin.getAllAnnouncements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



%>

<h1>User:  <%= curr %></h1>

<div class="nav-buttons">
    <a href="Quizzes"><button>Quizzes</button></a>
    <a href="createQuiz.jsp"><button>Create a Quiz</button></a>
    <a href="index.jsp"><button>Logout</button></a>
    <a href="FriendList.jsp"><button>Friends</button></a>
    <%
        if(acc.isAdmin(curr)){
    %>
    <a href="AdminControlPanelServlet"><button>Admin Control Panel</button></a>
    <%
        }
    %>
</div>

<form action="filteredUsers.jsp" method="get" class="search-form">
    <input type="search" name="query" placeholder="Search..." />
    <button type="submit">Search</button>
</form>

<!-- Announcements Section -->
<div class="announcements-section">
    <h3>Announcements</h3>
    <%
        if (announcements == null || announcements.isEmpty()) {
    %>
    <p>No announcements at this time.</p>
    <%
    } else {
        for (Announcement ann : announcements) {
    %>
    <div class="announcement-item">
        <div class="announcement-title"><%= ann.getTitle() %></div>
        <div class="announcement-message"><%= ann.getMessage() %></div>
        <div class="announcement-date">Posted at: <%= ann.getPostedAt() %></div>
    </div>
    <%
            }
        }
    %>
</div>

<div class="messages">
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
    <div class="message-item">
        <a href="message.jsp?messageType=<%=type%>&from=<%=temp.from%>&message=<%=temp.message%>">
            <%=temp.from + " sent you a " + type %>
        </a>
    </div>
    <%
            }
        }
    %>
</div>

<h3>Your Recent Quiz History</h3>
<%
    if (quizHistory == null || quizHistory.isEmpty()) {
%>
<p>You haven't taken any quizzes yet.</p>
<%
} else {
%>
<table>
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

<form action="HistoryServlet" method="get" class="full-history">
    <button type="submit">View Full Quiz History</button>
</form>

</body>
</html>

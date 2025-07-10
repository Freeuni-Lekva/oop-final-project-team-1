<%@ page import="dao.AccountManagerDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Friends</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f8fa;
            color: #333;
            padding: 30px 20px;
            margin: 0;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #444;
        }
        .friend-list {
            max-width: 600px;
            margin: 0 auto 40px;
        }
        .friend-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: white;
            padding: 12px 20px;
            border-radius: 8px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            margin-bottom: 12px;
        }
        .friend-name {
            font-size: 1.1rem;
            font-weight: 600;
        }
        .friend-item form {
            margin: 0;
        }
        .friend-item button {
            background-color: #4a90e2;
            color: white;
            border: none;
            padding: 8px 14px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 0.95rem;
            transition: background-color 0.3s ease;
        }
        .friend-item button:hover {
            background-color: #357ABD;
        }
        .nav-buttons {
            text-align: center;
            margin-top: 40px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .nav-buttons form {
            display: inline;
            margin: 0;
        }
        .nav-buttons button {
            background-color: #66a6ff;
            color: white;
            border: none;
            padding: 10px 22px;
            font-size: 1rem;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .nav-buttons button:hover {
            background-color: #4a8fdc;
        }
        p.no-friends {
            text-align: center;
            font-style: italic;
            color: #666;
            margin-top: 50px;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>

<h1>Your Friends</h1>

<div class="friend-list">
    <%
        AccountManagerDAO am = (AccountManagerDAO) application.getAttribute("accountManager");
        ArrayList<String> friends = am.getFriends((String) session.getAttribute("userName"));

        if (friends != null && !friends.isEmpty()) {
            for (String friend : friends) {
    %>
    <div class="friend-item">
        <div class="friend-name"><%= friend %></div>
        <form action="TextArea.jsp" method="get">
            <input type="hidden" name="from" value="<%= friend %>" />
            <button type="submit">Send Message</button>
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p class="no-friends">No friends found.</p>
    <%
        }
    %>
</div>

<div class="nav-buttons">
    <form action="HomePage.jsp" method="get">
        <button type="submit">Home</button>
    </form>
    <form action="Quizzes" method="get">
        <button type="submit">Quizzes</button>
    </form>
    <form action="LogoutServlet" method="post">
        <button type="submit">Logout</button>
    </form>
</div>

</body>
</html>

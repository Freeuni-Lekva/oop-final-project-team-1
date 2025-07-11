<%--
  Created by IntelliJ IDEA.
  User: giorgi
  Date: 06-07-25
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int maxscore = (Integer) request.getAttribute("maxScore");
    int userscore = (Integer) request.getAttribute("score");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Score</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 400px;
            margin: 100px auto;
            background: white;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
            text-align: center;
        }
        p {
            font-size: 1.4rem;
            margin: 15px 0;
            color: #333;
        }
        .score {
            font-size: 2.2rem;
            font-weight: bold;
            color: #2c7be5;
        }
        button {
            margin: 10px 10px 0 10px;
            padding: 10px 25px;
            font-size: 1rem;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            background-color: #2c7be5;
            color: white;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #1a5cc8;
        }
        form {
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container">
    <p>Your Score</p>
    <p class="score"><%= userscore %> / <%= maxscore %></p>

    <form action="HomePage.jsp" method="get">
        <button type="submit">Go to Homepage</button>
    </form>

    <form action="Quizzes" method="get">
        <button type="submit">Go to Quizzes</button>
    </form>
</div>
</body>
</html>

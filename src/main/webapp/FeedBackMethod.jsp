<%--
  Created by IntelliJ IDEA.
  User: giorgiqavtaradze
  Date: 29.06.25
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Choose Feedback Mode</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f0f4f8;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }
    form {
      background: white;
      padding: 25px 35px;
      border-radius: 8px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.1);
      max-width: 400px;
      width: 100%;
    }
    h2 {
      margin-bottom: 20px;
      color: #2c3e50;
      text-align: center;
    }
    label {
      font-weight: bold;
      display: block;
      margin-bottom: 10px;
      color: #34495e;
    }
    input[type="radio"] {
      margin-right: 8px;
      margin-bottom: 12px;
    }
    input[type="submit"] {
      display: block;
      width: 100%;
      background-color: #2c7be5;
      color: white;
      border: none;
      padding: 12px;
      border-radius: 6px;
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    input[type="submit"]:hover {
      background-color: #1a5cc8;
    }
  </style>
</head>
<body>
<form action="TakeQuizServlet" method="get">
  <input type="hidden" name="quizId" value="<%= request.getParameter("quizId") %>">
  <input type="hidden" name="isSinglePage" value="false">

  <label>How do you want to receive feedback?</label>
  <input type="radio" name="immediateFeedback" value="true" required> Immediately after each question<br>
  <input type="radio" name="immediateFeedback" value="false"> After finishing all questions<br><br>

  <input type="submit" value="Start Quiz">
</form>
</body>
</html>

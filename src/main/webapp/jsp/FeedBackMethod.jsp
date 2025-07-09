<%--
  Created by IntelliJ IDEA.
  User: giorgiqavtaradze
  Date: 29.06.25
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Choose Feedback Mode</title>
</head>
<body>
<h2>Select Feedback Mode</h2>

<form action="TakeQuizServlet" method="get">
  <input type="hidden" name="quizId" value="<%= request.getParameter("quizId") %>">
  <input type="hidden" name="isSinglePage" value="false">
  <label>How do you want to receive feedback?</label><br>
  <input type="radio" name="immediateFeedback" value="true" required> Immediately after each question<br>
  <input type="radio" name="immediateFeedback" value="false"> After finishing all questions<br><br>

  <input type="submit" value="Start Quiz">
</form>
</body>
</html>
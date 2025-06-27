<%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/27/2025
  Time: 8:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Questions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  List<Questions> questions = (List<Questions>) request.getAttribute("questions");
  int quizId = (int) request.getAttribute("quizId");
%>

<html>
<head><title>Take Quiz</title></head>
<body>
<form action="SubmitQuiz" method="post">
  <input type="hidden" name="quizId" value="<%= quizId %>">
  <%
    int qNum = 1;
    for (Questions q : questions) {
  %>
  <div>
    <b>Question <%= qNum++ %>: <%= q.getQuestion() %></b><br>
    <input type="text" name="answer_<%= q.getId() %>"><br><br>
  </div>
  <%
    }
  %>
  <input type="submit" value="Submit Quiz">
</form>
</body>
</html>


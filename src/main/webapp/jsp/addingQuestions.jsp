<%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/27/2025
  Time: 7:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int quizId = Integer.parseInt(request.getParameter("quizId"));
    String quizType = request.getParameter("type");
    int numQuestions = Integer.parseInt(request.getParameter("num"));
%>
<html>
<head>
    <title>Add Questions</title>
</head>
<body>
<h2>Add <%= numQuestions %> Questions for Quiz (Type: <%= quizType %>)</h2>

<form action="addingQuestionsServlet" method="post">
    <input type="hidden" name="quizId" value="<%= quizId %>">
    <input type="hidden" name="quizType" value="<%= quizType %>">
    <input type="hidden" name="numQuestions" value="<%= numQuestions %>">

    <%
        for (int i = 1; i <= numQuestions; i++) {
    %>
    <fieldset>
        <legend>Question <%= i %></legend>
        <label>Question Text:</label><br>
        <textarea name="questionText<%= i %>" rows="2" cols="50" required></textarea><br><br>

        <% if (quizType.equals("response") || quizType.equals("fill")) { %>
        <label>Correct Answer:</label><br>
        <input type="text" name="correctAnswer<%= i %>" required><br>
        <% } else if (quizType.equals("picture")) { %>
        <label>Image URL:</label><br>
        <input type="text" name="imageUrl<%= i %>" required><br>
        <label>Correct Answer:</label><br>
        <input type="text" name="correctAnswer<%= i %>" required><br>
        <% } else if (quizType.equals("mcq")) { %>
        <label>Option 1:</label><input type="text" name="option1_<%= i %>" required><br>
        <label>Option 2:</label><input type="text" name="option2_<%= i %>" required><br>
        <label>Option 3:</label><input type="text" name="option3_<%= i %>" required><br>
        <label>Option 4:</label><input type="text" name="option4_<%= i %>" required><br>
        <label>Correct Option (1-4):</label><input type="number" name="correctOption<%= i %>" min="1" max="4" required><br>
        <% } %>
    </fieldset>
    <br>
    <%
        }
    %>

    <input type="submit" value="Submit Questions">
</form>
</body>
</html>


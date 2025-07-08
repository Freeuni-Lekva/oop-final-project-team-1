<%--
  Created by IntelliJ IDEA.
  User: lukss
  Date: 6/27/2025
  Time: 7:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int quizId = (Integer) request.getAttribute("quizId");
    int numResponseQuestions = Integer.parseInt(request.getParameter("numResponseQuestions"));
    int numFillQuestions = Integer.parseInt(request.getParameter("numFillQuestions"));
    int numPictureQuestions = Integer.parseInt(request.getParameter("numPictureQuestions"));
    int numMCQuestions = Integer.parseInt(request.getParameter("numMcQuestions"));
    int numQuestions = numFillQuestions+numMCQuestions+numPictureQuestions+numResponseQuestions;
%>
<html>
<head>
    <title>Add Questions</title>
</head>
<body>
<h2>Add <%= numQuestions %> Questions for Quiz  <%= request.getParameter("quizTitle") %></h2>

<form action="addingQuestionsServlet" method="post">
    <input type="hidden" name="quizId" value="<%= quizId %>">
    <input type="hidden" name="numFillQuestions" value="<%= numFillQuestions %>">
    <input type="hidden" name="numPictureQuestions" value="<%= numPictureQuestions %>">
    <input type="hidden" name="numResponseQuestions" value="<%= numResponseQuestions %>">
    <input type="hidden" name="numMCQuestions" value="<%= numMCQuestions %>">

    <%
        for (int i = 1; i <= numQuestions; i++) {
    %>
    <fieldset>
        <legend>Question <%= i %></legend>
        <label>Question Text:</label><br>
        <textarea name="questionText<%= i %>" rows="2" cols="50" required></textarea><br><br>
        <% if (i-1<numFillQuestions) { %>
        <label>“To mark the blank in your question, use [[blank]]. You can place it anywhere in the sentence.”</label><br>
        <label>Correct Answer:</label><br>
        <input type="text" name="correctAnswer<%= i %>" required><br>
        <% } else if (i-1<numFillQuestions+numPictureQuestions) { %>
        <label>Image URL:</label><br>
        <input type="text" name="imageUrl<%= i %>" required><br>
        <label>Correct Answer:</label><br>
        <input type="text" name="correctAnswer<%= i %>" required><br>
        <% } else if (i-1<numResponseQuestions+numFillQuestions+numPictureQuestions) { %>
        <label>Correct Answer:</label><br>
        <input type="text" name="correctAnswer<%= i %>" required><br>

        <% }else  { %>
        <label>Option 1:</label><input type="text" name="option1_<%= i %>" required><br>
        <label>Option 2:</label><input type="text" name="option2_<%= i %>" required><br>
        <label>Option 3:</label><input type="text" name="option3_<%= i %>" required><br>
        <label>Option 4:</label><input type="text" name="option4_<%= i %>" required><br>
        <label>Correct Option (1-4):</label><input type="number" name="correctAnswer<%= i %>" min="1" max="4" required><br>
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


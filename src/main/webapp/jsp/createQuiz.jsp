<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create A Quiz</title>
</head>
<body>
<h2>Create a New Quiz</h2>

<form action="CreateQuizServlet" method="post">
    <label for="quizTitle">Quiz Title:</label><br>
    <input type="text" id="quizTitle" name="quizTitle" required><br><br>

    <label for="numQuestions">Number of Questions:</label><br>
    <input type="number" id="numQuestions" name="numQuestions" min="1" required><br><br>

    <label for="quizType">Select Quiz Type:</label><br>
    <select id="quizType" name="quizType" required>
        <option value="response">Short Response</option>
        <option value="fill">Fill in the Blank</option>
        <option value="mcq">Multiple Choice</option>
        <option value="picture">Picture Response</option>
    </select><br><br>

    <input type="submit" value="Create Quiz">
</form>
</body>
</html>

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

    <label for="numResponseQuestions">Number of Response Questions:</label><br>
    <input type="number" id="numResponseQuestions" name="numResponseQuestions" min="0" required><br><br>

    <label for="numFillQuestions">Number of Fill In Questions:</label><br>
    <input type="number" id="numFillQuestions" name="numFillQuestions" min="0" required><br><br>

    <label for="numPictureQuestions">Number of Picture Questions:</label><br>
    <input type="number" id="numPictureQuestions" name="numPictureQuestions" min="0" required><br><br>

    <label for="numMcQuestions">Number of MC Questions:</label><br>
    <input type="number" id="numMcQuestions" name="numMcQuestions" min="0" required><br><br>

    <label>Should questions appear in random order?</label><br>
    <input type="radio" name="isRandom" value="true" required> Yes<br>
    <input type="radio" name="isRandom" value="false"> No<br>

    <input type="submit" value="Create Quiz">
</form>
</body>
</html>

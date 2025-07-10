<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create A Quiz</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #89f7fe, #66a6ff);
            margin: 0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            color: #333;
        }
        .container {
            background: white;
            padding: 40px 30px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 450px;
            box-sizing: border-box;
            animation: fadeIn 1s ease forwards;
        }
        h2 {
            margin-bottom: 25px;
            color: #444;
            text-align: center;
        }
        label {
            font-weight: 600;
            display: block;
            margin-bottom: 8px;
            color: #333;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 12px 15px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.3s;
            box-sizing: border-box;
        }
        input[type="text"]:focus,
        input[type="number"]:focus {
            border-color: #66a6ff;
            outline: none;
            box-shadow: 0 0 8px rgba(102, 166, 255, 0.5);
        }
        .radio-group {
            margin-bottom: 20px;
        }
        .radio-group label {
            font-weight: 600;
            color: #333;
        }
        .radio-group input[type="radio"] {
            margin-right: 6px;
            cursor: pointer;
        }
        input[type="submit"] {
            width: 100%;
            padding: 14px 0;
            background: #66a6ff;
            border: none;
            border-radius: 8px;
            color: white;
            font-weight: 700;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        input[type="submit"]:hover {
            background: #4a8fdc;
        }
        .error-message {
            color: #cc0000;
            margin-bottom: 20px;
            font-weight: 600;
            text-align: center;
        }
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Create a New Quiz</h2>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <div class="error-message"><%= errorMessage %></div>
    <%
        }
    %>

    <form action="CreateQuizServlet" method="post">
        <label for="quizTitle">Quiz Title:</label>
        <input type="text" id="quizTitle" name="quizTitle" required>

        <label for="numResponseQuestions">Number of Response Questions:</label>
        <input type="number" id="numResponseQuestions" name="numResponseQuestions" min="0" required>

        <label for="numFillQuestions">Number of Fill In Questions:</label>
        <input type="number" id="numFillQuestions" name="numFillQuestions" min="0" required>

        <label for="numPictureQuestions">Number of Picture Questions:</label>
        <input type="number" id="numPictureQuestions" name="numPictureQuestions" min="0" required>

        <label for="numMcQuestions">Number of MC Questions:</label>
        <input type="number" id="numMcQuestions" name="numMcQuestions" min="0" required>

        <div class="radio-group">
            <label>Should questions appear in random order?</label>
            <input type="radio" name="isRandom" value="true" required> Yes<br>
            <input type="radio" name="isRandom" value="false"> No
        </div>

        <input type="submit" value="Create Quiz">
    </form>
</div>
</body>
</html>

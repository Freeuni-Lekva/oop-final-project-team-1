<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int quizId = (Integer) request.getAttribute("quizId");
    int numResponseQuestions = Integer.parseInt(request.getParameter("numResponseQuestions"));
    int numFillQuestions = Integer.parseInt(request.getParameter("numFillQuestions"));
    int numPictureQuestions = Integer.parseInt(request.getParameter("numPictureQuestions"));
    int numMCQuestions = Integer.parseInt(request.getParameter("numMcQuestions"));
    int numQuestions = numFillQuestions + numMCQuestions + numPictureQuestions + numResponseQuestions;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Questions</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #89f7fe, #66a6ff);
            margin: 0;
            padding: 30px;
            color: #333;
        }
        h2 {
            margin-bottom: 30px;
            text-align: center;
            color: #222;
        }
        form {
            max-width: 700px;
            margin: 0 auto;
            background: white;
            padding: 25px 30px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.15);
        }
        fieldset {
            border: 2px solid #66a6ff;
            border-radius: 12px;
            padding: 20px 25px;
            margin-bottom: 30px;
            background: #f5faff;
        }
        legend {
            font-weight: 700;
            font-size: 1.1rem;
            color: #4a8fdc;
            padding: 0 10px;
        }
        label {
            font-weight: 600;
            display: block;
            margin-bottom: 8px;
            color: #333;
        }
        textarea {
            width: 100%;
            padding: 10px 12px;
            font-size: 1rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            resize: vertical;
            transition: border-color 0.3s ease;
        }
        textarea:focus {
            border-color: #66a6ff;
            outline: none;
            box-shadow: 0 0 6px rgba(102, 166, 255, 0.6);
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px 12px;
            font-size: 1rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            margin-bottom: 15px;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus,
        input[type="number"]:focus {
            border-color: #66a6ff;
            outline: none;
            box-shadow: 0 0 6px rgba(102, 166, 255, 0.6);
        }
        input[type="submit"] {
            display: block;
            width: 100%;
            padding: 14px 0;
            background: #66a6ff;
            border: none;
            border-radius: 10px;
            color: white;
            font-weight: 700;
            font-size: 1.1rem;
            cursor: pointer;
            transition: background 0.3s ease;
            margin-top: 10px;
        }
        input[type="submit"]:hover {
            background: #4a8fdc;
        }

        fieldset label[for] {
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h2>Add <%= numQuestions %> Questions for Quiz "<%= request.getParameter("quizTitle") %>"</h2>

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
        <label for="questionText<%= i %>">Question Text:</label>
        <textarea id="questionText<%= i %>" name="questionText<%= i %>" rows="2" required></textarea>

        <% if (i-1 < numFillQuestions) { %>
        <label>“To mark the blank in your question, use [[blank]]. You can place it anywhere in the sentence.”</label>
        <label for="correctAnswer<%= i %>">Correct Answer:</label>
        <input type="text" id="correctAnswer<%= i %>" name="correctAnswer<%= i %>" required>
        <% } else if (i-1 < numFillQuestions + numPictureQuestions) { %>
        <label for="imageUrl<%= i %>">Image URL:</label>
        <input type="text" id="imageUrl<%= i %>" name="imageUrl<%= i %>" required>
        <label for="correctAnswer<%= i %>">Correct Answer:</label>
        <input type="text" id="correctAnswer<%= i %>" name="correctAnswer<%= i %>" required>
        <% } else if (i-1 < numResponseQuestions + numFillQuestions + numPictureQuestions) { %>
        <label for="correctAnswer<%= i %>">Correct Answer:</label>
        <input type="text" id="correctAnswer<%= i %>" name="correctAnswer<%= i %>" required>
        <% } else { %>
        <label for="option1_<%= i %>">Option 1:</label>
        <input type="text" id="option1_<%= i %>" name="option1_<%= i %>" required>
        <label for="option2_<%= i %>">Option 2:</label>
        <input type="text" id="option2_<%= i %>" name="option2_<%= i %>" required>
        <label for="option3_<%= i %>">Option 3:</label>
        <input type="text" id="option3_<%= i %>" name="option3_<%= i %>" required>
        <label for="option4_<%= i %>">Option 4:</label>
        <input type="text" id="option4_<%= i %>" name="option4_<%= i %>" required>
        <label for="correctAnswer<%= i %>">Correct Option (1-4):</label>
        <input type="number" id="correctAnswer<%= i %>" name="correctAnswer<%= i %>" min="1" max="4" required>
        <% } %>
    </fieldset>
    <% } %>

    <input type="submit" value="Submit Questions">
</form>

</body>
</html>

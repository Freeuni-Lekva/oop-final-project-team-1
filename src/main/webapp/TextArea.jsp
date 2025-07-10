<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Messenger</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #eef2f7;
            margin: 0;
            padding: 30px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }
        form {
            background-color: #fff;
            padding: 25px 30px;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
            max-width: 450px;
            width: 100%;
        }
        label {
            font-weight: 600;
            font-size: 1.1rem;
            display: block;
            margin-bottom: 8px;
            color: #333;
        }
        textarea {
            width: 100%;
            font-size: 1rem;
            padding: 10px;
            border-radius: 8px;
            border: 1.5px solid #ccc;
            resize: vertical;
            transition: border-color 0.3s ease;
        }
        textarea:focus {
            border-color: #4a90e2;
            outline: none;
        }
        button {
            background-color: #4a90e2;
            color: white;
            border: none;
            padding: 12px 25px;
            margin-top: 15px;
            border-radius: 8px;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #357ABD;
        }
    </style>
</head>
<body>
<form action="textMessage" method="post">
    <label for="messageText">Message:</label>
    <textarea id="messageText" name="messageText" rows="5" required></textarea>

    <input type="hidden" name="from" value="<%=request.getParameter("from")%>" />
    <input type="hidden" name="action" value="<%=request.getParameter("action")%>" />
    <input type="hidden" name="message" value="<%=request.getParameter("message")%>" />

    <button type="submit">Send</button>
</form>
</body>
</html>

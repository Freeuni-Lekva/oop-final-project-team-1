<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Name Already In Use</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f9f9f9;
            margin: 0;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
            color: #333;
        }
        .container {
            background: white;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 12px 25px rgba(0,0,0,0.1);
            max-width: 450px;
            width: 100%;
            box-sizing: border-box;
            text-align: center;
        }
        h1 {
            color: #d9534f;
            font-size: 1.6rem;
            margin-bottom: 20px;
        }
        p {
            font-weight: 600;
            margin-bottom: 25px;
            color: #555;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            margin: 8px 0 20px;
            font-size: 1rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: #66a6ff;
            outline: none;
            box-shadow: 0 0 8px rgba(102,166,255,0.5);
        }
        input[type="submit"] {
            background-color: #66a6ff;
            color: white;
            border: none;
            padding: 14px 0;
            width: 100%;
            font-size: 1.1rem;
            border-radius: 10px;
            font-weight: 700;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #4a8fdc;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>The Name "<%= request.getParameter("name") %>" is already in use</h1>
    <p>Please enter another name and password</p>
    <form action="RegisterServlet" method="post">
        <input type="text" name="name" placeholder="User Name" required />
        <input type="password" name="password" placeholder="Password" required />
        <input type="submit" value="Register" />
    </form>
</div>
</body>
</html>

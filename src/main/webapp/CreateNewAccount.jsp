<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create New Account</title>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #89f7fe, #66a6ff);
            display: flex;
            justify-content: center;
            align-items: center;
            color: #333;
        }
        .container {
            background: white;
            padding: 50px 40px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 350px;
            text-align: center;
            animation: fadeIn 1s ease;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 1.8rem;
            color: #444;
        }
        p {
            margin-bottom: 30px;
            color: #666;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 8px;
            transition: all 0.3s;
            font-size: 1rem;
        }
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: #66a6ff;
            box-shadow: 0 0 5px rgba(102,166,255,0.5);
            outline: none;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #66a6ff;
            border: none;
            border-radius: 8px;
            color: white;
            font-size: 1rem;
            cursor: pointer;
            margin-top: 15px;
            transition: background 0.3s;
        }
        input[type="submit"]:hover {
            background: #4a8fdc;
        }
        @keyframes fadeIn {
            from {opacity: 0; transform: translateY(-20px);}
            to {opacity: 1; transform: translateY(0);}
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Create New Account</h1>
    <p>Please enter proposed name and password</p>
    <form action="RegisterServlet" method="post">
        <input type="text" name="name" placeholder="User Name" required/>
        <input type="password" name="password" placeholder="Password" required/>
        <input type="submit" value="Create Account"/>
    </form>
</div>
</body>
</html>

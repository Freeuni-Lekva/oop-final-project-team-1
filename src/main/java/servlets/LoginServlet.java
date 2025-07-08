package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.AccountManagerDAO;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            AccountManagerDAO accManager = (AccountManagerDAO) request.getServletContext().getAttribute("accountManager");
        if(accManager.isCorrectPas(request.getParameter("name"), request.getParameter("password"))){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");

            HttpSession session = request.getSession();
            session.setAttribute("userName", request.getParameter("name"));
            rd.forward(request,response);
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WrongCredentials.jsp");
            rd.forward(request,response);
        }

    }
    }


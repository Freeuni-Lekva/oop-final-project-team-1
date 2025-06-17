package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.AccountManager;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountManager accManager = (AccountManager) request.getServletContext().getAttribute("accountManager");
        if(accManager.checkIfAccountExists(request.getParameter("name"))){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/NameInUse.jsp");
            rd.forward(request,response);
        }else{
            accManager.createAccount(request.getParameter("name"), request.getParameter("password"));
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
            rd.forward(request,response);      }
    }
}

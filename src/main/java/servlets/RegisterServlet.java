package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.AccountManagerDAO;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountManagerDAO accManager = (AccountManagerDAO) request.getServletContext().getAttribute("accountManager");
        if(accManager.checkIfAccountExists(request.getParameter("name"))){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/NameInUse.jsp");

            rd.forward(request,response);
        }else{
            accManager.createAccount(request.getParameter("name"), request.getParameter("password"));
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("userName", request.getParameter("name"));
            rd.forward(request,response);      }
    }
}

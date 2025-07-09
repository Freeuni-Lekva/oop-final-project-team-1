package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.AdminDAO;

import java.io.IOException;

@WebServlet("/RemoveUserServlet")
public class RemoveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AdminDAO admin = (AdminDAO) req.getServletContext().getAttribute("admin");
        String username = req.getParameter("username");
        admin.removeUser(username);
        RequestDispatcher dispatcher = req.getRequestDispatcher("AdminControlPanelServlet");
        dispatcher.forward(req, resp);
    }
}

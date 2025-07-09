package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.AdminDAO;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/promoteToAdminServlet")
public class promoteToAdminServlet extends HttpServlet {


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO admin = (AdminDAO) req.getServletContext().getAttribute("admin");
        String username = req.getParameter("username");
        try {
            admin.promoteToAdmin(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher rd = req.getRequestDispatcher("AdminControlPanelServlet");
        rd.forward(req, resp);

    }
}

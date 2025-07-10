package servlets;
import dao.AdminDAO;
import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Option;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
@WebServlet("/AddAnnouncementServlet")
public class AddAnnouncementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String message = request.getParameter("message");
        AdminDAO admin = (AdminDAO) request.getServletContext().getAttribute("admin");
        try {
            admin.addAnnouncement(title, message);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminControlPanelServlet");
        dispatcher.forward(request, response);

    }
}

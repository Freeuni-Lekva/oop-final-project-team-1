package servlets;

import dao.AdminDAO;
import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RemoveQuizHistoryServlet")
public class RemoveQuizHistoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO adminDao = (AdminDAO) req.getServletContext().getAttribute("admin");
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        try {
           adminDao.clearQuizHistory(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("AdminControlPanelServlet");
        dispatcher.forward(req, resp);
    }
    }



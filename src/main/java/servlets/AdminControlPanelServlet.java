package servlets;

import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.AccountManagerDAO;
import dao.AdminDAO;
import dao.MessagesDAO;

import java.io.IOException;


@WebServlet("/AdminControlPanelServlet")
    public class AdminControlPanelServlet extends HttpServlet {

    @Override
        public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            AccountManagerDAO accManager = (AccountManagerDAO) request.getServletContext().getAttribute("accountManager");
            QuizDAO quiz = (QuizDAO) request.getServletContext().getAttribute("quizDAO");
            AdminDAO admin = (AdminDAO) request.getServletContext().getAttribute("admin");
            MessagesDAO messages = (MessagesDAO) request.getServletContext().getAttribute("messages");
            request.setAttribute("quizDAO", quiz);
            request.setAttribute("admin", admin);
            request.setAttribute("messages", messages);
            request.setAttribute("accountManager", accManager);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
            dispatcher.forward(request, response);
        }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    }


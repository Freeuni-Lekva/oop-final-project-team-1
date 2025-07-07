package servlets;

import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.AccountManagerDAO;
import models.AdminDAO;
import models.MessagesDAO;

import java.io.IOException;


    public class AdminControlPanelServlet extends HttpServlet {
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            AccountManagerDAO accManager = (AccountManagerDAO) request.getServletContext().getAttribute("accountManager");
            QuizDAO quiz = (QuizDAO) request.getServletContext().getAttribute("quiz");
            AdminDAO admin = (AdminDAO) request.getServletContext().getAttribute("admin");
            MessagesDAO messages = (MessagesDAO) request.getServletContext().getAttribute("messages");





        }
    }


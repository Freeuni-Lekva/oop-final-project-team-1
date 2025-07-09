package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.AccountManagerDAO;
import models.MessagesDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/friend")
public class FriendReqServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountManagerDAO acc = (AccountManagerDAO) request.getServletContext().getAttribute("accountManager");
        MessagesDAO ms = (MessagesDAO) request.getServletContext().getAttribute("messages");
        String curr = (String) request.getSession().getAttribute("userName");



        if ("accept".equals(request.getParameter("action"))) {
            try {
                acc.addFriend(curr, request.getParameter("from"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            MessagesDAO.Message m = new MessagesDAO.Message(request.getParameter("from"), "System", curr + " Has Accepted Your Friend Request", false);
            ms.addMessage(m);
            String mes = request.getParameter("message");
            MessagesDAO.Message toRemove = new MessagesDAO.Message(curr, request.getParameter("from"), mes, true);
            ms.removeMessage(toRemove);
            try {
                acc.removeFriendRequest(request.getParameter("from"), curr);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
            ;
            if (request.getParameter("from").equals("filteredUser"))
                rd = getServletContext().getRequestDispatcher("/filteredUser.jsp");
            rd.forward(request, response);
        }
    }


    }



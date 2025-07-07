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
import java.util.ArrayList;

@WebServlet("/textMessage")
public class MessageLogicServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountManagerDAO accManager = (AccountManagerDAO) request.getServletContext().getAttribute("accountManager");
       String curr = (String) request.getSession().getAttribute("userName");
        MessagesDAO me  = (MessagesDAO) request.getServletContext().getAttribute("messages");
        MessagesDAO.Message tmp = new MessagesDAO.Message(request.getParameter("from"),curr,request.getParameter("messageText"),false);


        if(request.getParameter("action")!=null && request.getParameter("action").equals("Delete")) {
            respondToAction(request, curr, me);
        }else if(request.getParameter("action")!=null && request.getParameter("action").equals("Reply")){
            respondToAction(request, curr, me);
            me.addMessage(tmp);
        }else{
            me.addMessage(tmp);
        }




            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
            rd.forward(request,response);
        }

    private void respondToAction(HttpServletRequest request, String curr, MessagesDAO me) {

        MessagesDAO.Message deletedMessage = new MessagesDAO.Message(curr,request.getParameter("from"),request.getParameter("message"),false);
        me.removeMessage(deletedMessage);
    }

}



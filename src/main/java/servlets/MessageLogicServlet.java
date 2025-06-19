package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.AccountManager;
import models.Messages;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/textMessage")
public class MessageLogicServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountManager accManager = (AccountManager) request.getServletContext().getAttribute("accountManager");
        String curr = (String) request.getSession().getAttribute("userName");
        Messages me  = (Messages) request.getServletContext().getAttribute("messages");
        Messages.Message tmp = new Messages.Message(request.getParameter("from"),curr,request.getParameter("messageText"),false);


        if(request.getParameter("action")!=null && request.getParameter("action").equals("Delete")) {
            ArrayList<Messages.Message> userMessages = me.getMessages(curr);
            Messages.Message deletedMessage = new Messages.Message(curr,request.getParameter("from"),request.getParameter("message"),false);
            int removeIndex = -1;
            for(int i = 0; i<userMessages.size(); i++){
                if(userMessages.get(i).from.equals(deletedMessage.from) && userMessages.get(i).message.equals(deletedMessage.message)){
                    removeIndex = i;
                    break;
                }
            }
            userMessages.remove(removeIndex);
        }else if(request.getParameter("action")!=null && request.getParameter("action").equals("Reply")){
            ArrayList<Messages.Message> userMessages = me.getMessages(curr);
            Messages.Message deletedMessage = new Messages.Message(curr,request.getParameter("from"),request.getParameter("message"),false);
            int removeIndex = -1;
            for(int i = 0; i<userMessages.size(); i++){
                if(userMessages.get(i).from.equals(deletedMessage.from) && userMessages.get(i).message.equals(deletedMessage.message)){
                    removeIndex = i;
                    break;
                }
            }
            userMessages.remove(removeIndex);
            me.addMessage(tmp);
        }else{
            me.addMessage(tmp);
        }




            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
            rd.forward(request,response);
        }

    }



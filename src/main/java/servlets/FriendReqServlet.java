package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.AccountManager;
import models.Messages;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/friend")
public class FriendReqServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountManager acc = (AccountManager) request.getServletContext().getAttribute("accountManager");
        Messages ms = (Messages) request.getServletContext().getAttribute("messages");
        String curr = (String) request.getSession().getAttribute("userName");

        if("accept".equals(request.getParameter("action"))) {
            acc.addFriend(curr, request.getParameter("from"));
            Messages.Message m = new Messages.Message(request.getParameter("from"), "Program", curr + " Has Accepted Your Friend Request", false);
            ms.addMessage(m);
            String mes = request.getParameter("message");
            ArrayList<Messages.Message> tmp = ms.getMessages(curr);
            int removeIndex = -1;
            for(int i = 0; i<ms.getMessages(curr).size(); i++){
                if(tmp.get(i).from.equals(request.getParameter("from")) && tmp.get(i).message.equals(mes)){
                    removeIndex = i;
                    break;
                }
            }
            tmp.remove(removeIndex);
            ms.messages.put(curr,tmp);
            ArrayList<String> sent = (ArrayList<String>) request.getSession().getAttribute(request.getParameter("from")+"SendFriends");
            sent.remove(curr);
            request.getSession().setAttribute(request.getParameter("from")+"SendFriends",sent);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");;
            if(request.getParameter("from").equals("filteredUser")) rd = getServletContext().getRequestDispatcher("/filteredUser.jsp");
            rd.forward(request, response);
        } else if ("reject".equals(request.getParameter("action"))) {
            Messages.Message m = new Messages.Message(request.getParameter("from"), "Program", curr + " Has Rejected Your Friend Request", false);
            ms.addMessage(m);
            ArrayList<String> sent = (ArrayList<String>) request.getSession().getAttribute(request.getParameter("from")+"SendFriends");
            sent.remove(curr);
            request.getSession().setAttribute(request.getParameter("from")+"SendFriends",sent);
            String mes = request.getParameter("message");
            ArrayList<Messages.Message> tmp = ms.getMessages(curr);
            int removeIndex = -1;
            for(int i = 0; i<ms.getMessages(curr).size(); i++){
                if(tmp.get(i).from.equals(request.getParameter("from")) && tmp.get(i).message.equals(mes)){
                    removeIndex = i;
                    break;
                }
            }
            tmp.remove(removeIndex);
            ms.messages.put(curr,tmp);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");;
            if(request.getParameter("from").equals("filteredUser")) rd = getServletContext().getRequestDispatcher("/filteredUser.jsp");
            rd.forward(request, response);

        }
        }

    }



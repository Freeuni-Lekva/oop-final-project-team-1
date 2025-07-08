package servlets;

import dao.AccountManagerDAO;
import dao.AdminDAO;
import dao.MessagesDAO;
import dao.QuizDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionListener;
import models.*;

import java.sql.Connection;
import java.util.ArrayList;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {


    ArrayList<Quiz> popularQuizzes = new ArrayList<Quiz>();
    ArrayList<Quiz> quizzes=new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Connection dbConnection = MyDataBase.getConnection();

        ServletContext context = sce.getServletContext();
        AccountManagerDAO accountManagerDAO = new AccountManagerDAO(dbConnection);
        MessagesDAO messagesDAO = new MessagesDAO(dbConnection);
        AdminDAO adminDAO = new AdminDAO(dbConnection);
        context.setAttribute("accountManager", accountManagerDAO);
        context.setAttribute("messages", messagesDAO);
        context.setAttribute("popularQuizzes", popularQuizzes);
        context.setAttribute("quizzes", quizzes);
        context.setAttribute("admin", adminDAO);
        QuizDAO quizDAO = new QuizDAO(dbConnection);
        context.setAttribute("quizDAO", quizDAO);

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}

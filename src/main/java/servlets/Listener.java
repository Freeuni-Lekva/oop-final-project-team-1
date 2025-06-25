package servlets;

import dao.QuizDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionListener;
import models.AccountManager;
import models.Messages;
import models.MyDataBase;
import models.Quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {
    AccountManager accManager = new AccountManager();
    Messages messages = new Messages();
    ArrayList<Quiz> popularQuizzes = new ArrayList<Quiz>();
    ArrayList<Quiz> quizzes=new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("accountManager", accManager);
        context.setAttribute("messages", messages);
        context.setAttribute("popularQuizzes", popularQuizzes);
        context.setAttribute("quizzes", quizzes);
        Connection dbConnection = MyDataBase.getConnection();
        QuizDAO quizDAO = new QuizDAO(dbConnection);
        context.setAttribute("quizDAO", quizDAO);

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}

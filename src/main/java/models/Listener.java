package models;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener {
    AccountManager accManager = new AccountManager();
    Messages messages = new Messages();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("accountManager", accManager);
        context.setAttribute("messages", messages);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}

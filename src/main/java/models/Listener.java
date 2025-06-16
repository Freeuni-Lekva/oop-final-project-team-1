package models;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {
    AccountManager accManager = new AccountManager();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("accountManager", accManager);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

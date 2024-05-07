package ict.servlet;

import ict.db.AsmDB;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppContextListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
   
        String dbUrl = sce.getServletContext().getInitParameter("dbUrl");
        String dbUser = sce.getServletContext().getInitParameter("dbUser");
        String dbPassword = sce.getServletContext().getInitParameter("dbPassword");

        AsmDB db = new AsmDB(dbUrl, dbUser, dbPassword);

        
        scheduler.scheduleAtFixedRate(() -> {
            try {
                db.checkOverdueItemsAndNotify();
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Charles
 */
public class Config implements ServletContextListener{
    //valeur contenu dans le fichier web.xml - Joel
    public static String URL = "";
    public static String DB_HOST = "";
    public static String DB_USER = "";
    public static String DB_PWD = "";
    public static String DB_NAME = "";
    public static String DRIVER = ""; 

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        ServletContext application = sce.getServletContext();
        
        Config.URL = application.getInitParameter("urlDb");
        Config.DB_USER = application.getInitParameter("userDB");
        Config.DB_PWD = application.getInitParameter("passwordDB");
        Config.DRIVER = application.getInitParameter("piloteJDBC");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

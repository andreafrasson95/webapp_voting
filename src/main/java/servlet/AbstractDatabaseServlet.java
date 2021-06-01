package it.frassi.servlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class AbstractDatabaseServlet extends HttpServlet {


    //Connection pool to the  database
    private DataSource ds;

    public void init(ServletConfig config) throws ServletException{

        //JNDI lookup context
        InitialContext cxt;

        try{
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/poll-webapp");
        }catch(NamingException e){
            ds = null;

            throw new ServletException(
                    String.format("Impossible to access the connection pool to the database: %s",e.getMessage()));
        }
    }

    public void destroy(){ ds = null;}

    protected final DataSource getDataSource(){ return ds;}
}

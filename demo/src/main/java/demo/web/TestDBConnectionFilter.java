package demo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;

public class TestDBConnectionFilter implements Filter {

    private DataSource dataSource;

    private boolean stop = false;

    /**
     * 持续测试数据库链接可用性
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ds = filterConfig.getInitParameter("dataSource");
        if (ds == null) {
            ds = "java:comp/env/jdbc/test";
        }
        try {
            Context initCtx = new InitialContext();
            dataSource = (DataSource) initCtx.lookup(ds);
        } catch (NamingException e1) {
            e1.printStackTrace();
            System.err.println("datasource " + ds + " not found !");
            return;
        }

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                while (true) {
                    try {
                        conn = dataSource.getConnection();
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery("SELECT * FROM DUAL");
                        System.out.println("[INFO]  OK !");
                    } catch (Exception e) {
                        System.err.println("[ERROR] " + e);
                        // e.printStackTrace();
                    } finally {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            rs = null;
                        }
                        if (stmt != null) {
                            try {
                                stmt.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            stmt = null;
                        }
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            conn = null;
                        }
                    }
                    if (stop) {
                        break;
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println("[INFO]  STOP !");
            }
        });
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        stop = true;
    }

}

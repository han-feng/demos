package demo.web;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TestServlet extends GenericServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {
        res.getWriter()
                .println(
                        "<html><body>TestServlet <a href='index.jsp'>Goto index.jsp</a></body></html>");
        res.flushBuffer();
    }

}

package demo.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class TestFilter implements Filter {

    private static final Map<String, String> WELCOME_FILE_CACHE = new HashMap<String, String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        System.out.println("############ uri = " + req.getRequestURI()
                + "\n######### contextpath = " + req.getContextPath()
                + "\n######### pathinfo = " + req.getPathInfo()
                + "\n######### servletpath = " + req.getServletPath());

        if ("/".equals(req.getServletPath()) && req.getPathInfo() == null) {
            String contextpath = req.getContextPath();

            // 将“/demo”重定向到“/demo/”
            if (contextpath.equals(req.getRequestURI())) {
                ((HttpServletResponse) response)
                        .sendRedirect(contextpath + "/");
                return;
            }

            // 使用CACHE减少web.xml解析消耗，只解析一次
            String welcomefile = WELCOME_FILE_CACHE.get(contextpath);
            if (welcomefile == null) {
                welcomefile = "$##$"; // 代表找不到有效的 welcomefile
                ServletContext context = req.getServletContext();

                // 获取web.xml中的welcome-file-list
                List<String> welcomeFiles = new ArrayList<String>();
                InputStream in = context
                        .getResourceAsStream("/WEB-INF/web.xml");

                try {
                    // 使用 JAXP SAX 解析 web.xml
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    XMLReader reader = parser.getXMLReader();

                    MyContentHandler handler = new MyContentHandler();
                    handler.welcomeFiles = welcomeFiles;

                    reader.setContentHandler(handler);
                    reader.parse(new InputSource(in));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (String file : welcomeFiles) {
                    if (!file.startsWith("/"))
                        file = "/" + file;

                    if (context.getResource(file) != null) {
                        welcomefile = file;
                        break;
                    }
                }
                WELCOME_FILE_CACHE.put(contextpath, welcomefile);
            }
            if (!"$##$".equals(welcomefile)) {
                request.getRequestDispatcher(welcomefile).forward(request,
                        response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    static class MyContentHandler extends DefaultHandler {

        List<String> welcomeFiles;
        String currentTagName;

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes atts) throws SAXException {
            currentTagName = qName;
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            currentTagName = null;
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if ("welcome-file".equals(currentTagName)) {
                welcomeFiles.add(new String(ch, start, length));
            }
        }

    }

}

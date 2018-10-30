package demo.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestDumpFilter implements Filter {

	private static AtomicInteger maxId = new AtomicInteger();

	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		StringBuilder msg = new StringBuilder();
		HttpServletRequest req = (HttpServletRequest) request;
		int id = maxId.incrementAndGet();

		msg.append("dump request header [" + id + "]\n");
		msg.append(req.getMethod() + " " + req.getRequestURI());
		String qs = req.getQueryString();
		if (qs != null) {
			msg.append("?" + qs);
		}
		msg.append(" " + req.getProtocol() + "\n");

		Enumeration<String> names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getHeader(name);
			msg.append("\t" + name + ": " + value + "\n");
		}

		filterConfig.getServletContext().log(msg.toString());

		chain.doFilter(request, response);

		HttpServletResponse resp = (HttpServletResponse) response;
		msg.setLength(0);
		msg.append("dump response header [" + id + "]\n");

		for (String name : resp.getHeaderNames()) {
			String value = resp.getHeader(name);
			msg.append("\t" + name + ": " + value + "\n");
		}

		// msg.append("Content-Type: " + resp.getContentType());

		filterConfig.getServletContext().log(msg.toString());
	}

	@Override
	public void destroy() {
	}

}

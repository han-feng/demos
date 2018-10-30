package demo.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ChunkedInputTestServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		InputStream stream = req.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		try {
			int c = 0;
			byte[] bs = new byte[1024];
			while ((c = stream.read(bs)) > 0) {
				out.write(bs, 0, c);
			}
			byte[] result = out.toByteArray();
			System.out.println("Input lenght : " + result.length);
		} finally {
			out.close();
		}
	}

}

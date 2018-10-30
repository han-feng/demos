package demo.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class ChunkedInputClient {

	public static void main(String[] args) {
		HttpURLConnection conn = null;
		DataOutputStream os = null;
		try {
			URL url = new URL("http://127.0.0.1:8080/demo/chunkedInputTest");
			conn = (HttpURLConnection) url
					.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));
			conn.setRequestMethod("POST");
			conn.setChunkedStreamingMode(1024);
			conn.setDoOutput(true);
			conn.connect();
			os = new DataOutputStream(conn.getOutputStream());
			for (int i = 0; i < 1000; i++) {
				os.writeUTF("#" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}

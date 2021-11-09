package org.demos.drools.cep;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// import org.junit.Test;

/**
 * 测试HTTPS协议访问及Cookies管理
 *
 * @author han_feng
 *
 */
public class HttpsURLConnectionTest {

    private static final Logger LOG = LogManager
            .getLogger(HttpsURLConnectionTest.class);

    // @Test
    public void testCookie() throws IOException {
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);

        URL url = new URL("https://github.com/");
        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
                .openConnection();
        httpUrlConn.connect();

        int resultCode = httpUrlConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            CookieStore cookieJar = manager.getCookieStore();
            List<HttpCookie> cookies = cookieJar.getCookies();
            for (HttpCookie cookie : cookies) {
                LOG.info(cookie);
            }
        }
    }

}

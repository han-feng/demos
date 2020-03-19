package demos.bc;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class BCTest {

	@Test
	public void testBinToString() throws UnsupportedEncodingException {
		byte[] data = "Hello, Boun Cycastle!".getBytes("UTF-8");
		System.out.println("[Hex] " + Hex.toHexString(data));
		System.out.println("[B64] " + Base64.toBase64String(data));
	}

}

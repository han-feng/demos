package demos.bc;

import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

public class BCTest {

	@Test
	public void testBinToString() {
		testBase64("Hello, Boun Cycastle!");
		for (int i = 0; i < 10; i++) {
			testBase64(RandomStringUtils.randomAlphanumeric(10, 1000));
		}
	}

	private void testBase64(String str) {
		System.out.println("Test Base64: \"" + str + "\"");
		byte[] data = str.getBytes();
		String b64str = new String(Base64.encode(data));
		System.out.println("[Hex] " + new String(Hex.encode(data)));
		System.out.println("[B64] " + b64str);
		Assert.assertArrayEquals(data, Base64.decode(b64str));
		System.out.println("OK!");
	}

}

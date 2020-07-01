package demos.bc;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

public class JCATest {

	@Test
	public void testJCA() {

		display();

		Security.addProvider(new BouncyCastleProvider());

		System.out.println("\n\n########################\n");

		display();

	}

	private void display() {

		for (Provider provider : Security.getProviders()) {
			System.out.println(provider);
			System.out.println("-----------------------------");
			for (Service service : provider.getServices()) {
				System.out.println(service);
			}
			System.out.println("=============================");
		}
	}

}

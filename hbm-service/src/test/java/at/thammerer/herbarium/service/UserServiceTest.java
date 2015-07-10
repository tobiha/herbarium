package at.thammerer.herbarium.service;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

/**
 * @author thammerer
 */
public class UserServiceTest {

	@Test
	public void testName() throws Exception {
		String password = new BCryptPasswordEncoder().encode("password");
		System.out.println(password);

	}
}
package test.com.h2rd.refactoring.integration;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.web.UserResource;

public class UserIntegrationTest {
	
	@Test
	public void createUserTest() {
		UserResource userResource = new UserResource();
		
        String name = "integration";
        String email = "initial@integration.com";
        List<String> roles = Arrays.asList("admin", "master");
        
        Response response = userResource.addUser(name, email, roles);
        Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void updateUserTest() {
		UserResource userResource = new UserResource();

		String name = "updated";
        String email = "initial@integration.com";
        List<String> roles = Arrays.asList("admin", "master");
        
        Response response = userResource.updateUser(name, email, roles);
        Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void deleteUserTest() {
		UserResource userResource = new UserResource();

        String email = "initial@integration.com";
        
        Response response = userResource.deleteUser(email);
        Assert.assertEquals(200, response.getStatus());
	}
}

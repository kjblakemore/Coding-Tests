package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.usermanagement.User;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UserUnitTest {

    @Test
    public void userUnitTest() {
        User user;

        String name = "Fake Name";
        String email = "fake@email.com";
        List<String> roles = Arrays.asList("admin", "master");

        user = new User(name, email, roles);
        name = "New Name";
        roles = Arrays.asList("new role", "admin", "master");
        user.update(name, roles);

        Assert.assertEquals(name, user.getName());
        Assert.assertEquals(roles, user.getRoles());
    }
}
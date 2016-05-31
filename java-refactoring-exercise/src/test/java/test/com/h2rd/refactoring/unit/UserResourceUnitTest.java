package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import com.h2rd.refactoring.web.UserResource;
import junit.framework.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Arrays;

public class UserResourceUnitTest {

    UserResource userResource;
    UserDao userDao;

    @Test
    public void getUsersTest() {
        User user;

        userResource = new UserResource();
        userDao = UserDao.getUserDao();

        String name = "fake user";
        String email = "fake@user.com";
        List<String> roles = Arrays.asList("admin", "master");

        user = userDao.saveUser(name, email, roles);

        Response response = userResource.getUsers();
        Assert.assertEquals(200, response.getStatus());
    }
}

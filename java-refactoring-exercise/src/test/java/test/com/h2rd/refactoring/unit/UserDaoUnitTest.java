package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;

public class UserDaoUnitTest {
    @Test
    public void UserTest() {
        UserDao userDao;
        User user;
        String name, name1, name2;
        String email, email1, email2;
        List<String> roles, roles1, roles2;
        Collection<User> userValues;

        userDao = UserDao.getUserDao();

        name1 = "Fake Name";
        email1 = "fake@email.com";
        roles1 = Arrays.asList("admin", "master");

        /* Test saveUser */
        user = userDao.saveUser(name1, email1, roles1);
        Assert.assertNotNull(user);     

        user = userDao.findUser(email1);
        Assert.assertNotNull(user);     
        Assert.assertEquals(name1, user.getName());
        Assert.assertEquals(email1, user.getEmail());
        Assert.assertEquals(roles1, user.getRoles());

        /* Test delete previously saved user */
        user = userDao.deleteUser(email1);
        Assert.assertNotNull(user);

        /* Test deleting unsaved user */
        user = userDao.deleteUser(email1);
        Assert.assertNull(user);

        /* Test update user */
        user = userDao.saveUser(name1, email1, roles1);

        name2 = "New Name";
        roles2 = Arrays.asList("admin", "master", "another role");
        user = userDao.updateUser(name2, email1, roles2);
        Assert.assertNotNull(user);

        user = userDao.findUser(email1); 
        Assert.assertNotNull(user);    
        Assert.assertEquals(name2, user.getName());
        Assert.assertEquals(user.getEmail(), email1);
        Assert.assertEquals(user.getRoles(), roles2);

        /* Test getUsers */
        email2 = "another@email.com";
        user = userDao.saveUser(name1, email2, roles1);
        Assert.assertNotNull(user);

        userValues = userDao.getUsers();
        Assert.assertNotNull(userValues);
        Assert.assertEquals(2, userValues.size());

        for (User u: userValues) {
            name = u.getName();
            email = u.getEmail();
            roles = u.getRoles();
            if(email == email1) {
                Assert.assertEquals(name2, u.getName());
                Assert.assertEquals(email1, u.getEmail());
                Assert.assertEquals(roles2, u.getRoles());
            } else if(email == email2) {
                Assert.assertEquals(name1, u.getName());
                Assert.assertEquals(email2, u.getEmail());
                Assert.assertEquals(roles1, u.getRoles());
            } else Assert.fail("get Users: Invalid email");
        }

        userDao.deleteUser(email1);
        userDao.deleteUser(email2);
    }
}
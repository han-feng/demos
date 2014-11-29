package demos.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring-context*.xml")
@ActiveProfiles("test")
public class UserServiceTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private UserService userService;

    @Test
    public void testDao() {
        User user = userService.getUser(0);
        Assert.assertNull(user);

        for (int i = 0; i < 100; i++) {
            user = new User();
            user.setId(i);
            user.setName("User" + i);
            user.setLoginName("user" + i);
            user.setEmail("user" + i + "@test.com");
            Assert.assertEquals(1, userService.addUser(user));
        }

        for (int i = 0; i < 100; i++) {
            user = userService.getUser(i);
            Assert.assertNotNull(user);
            Assert.assertEquals(i, user.getId());
            Assert.assertEquals("User" + i, user.getName());
            Assert.assertEquals("user" + i, user.getLoginName());
            Assert.assertEquals("user" + i + "@test.com", user.getEmail());
        }

        user.setId(50);
        user.setName("TestName");
        user.setLoginName("TestLoginName");
        user.setEmail("TestEmail");
        Assert.assertEquals(1, userService.updateUser(user));

        user = userService.getUser(50);
        Assert.assertNotNull(user);
        Assert.assertEquals(50, user.getId());
        Assert.assertEquals("TestName", user.getName());
        Assert.assertEquals("TestLoginName", user.getLoginName());
        Assert.assertEquals("TestEmail", user.getEmail());

        user = new User();
        user.setName("ser");
        List<User> list = userService.findUser(user);
        Assert.assertEquals(99, list.size());

        user = new User();
        user.setName("TestName");
        list = userService.findUser(user);
        Assert.assertEquals(1, list.size());

        userService.deleteUser(50);
        list = userService.findUser(user);
        Assert.assertEquals(0, list.size());

        user.setName("User");
        list = userService.findUser(user);
        Assert.assertEquals(99, list.size());

    }
}

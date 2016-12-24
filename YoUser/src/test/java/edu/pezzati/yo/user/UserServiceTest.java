package edu.pezzati.yo.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;

import edu.pezzati.yo.user.model.User;

public class UserServiceTest {

    @Before
    public void init() {

    }

    @After
    public void destroy() {

    }

    public void createUser() {
	User user = Mockito.spy(new User());
	UserService userService = new UserService();
	user = userService.create(user);
	Assert.assertTrue(user.getId() != null);
    }

    public void readUserById() {

    }

    public void readUserByTextAttribute() {

    }

    public void updateUser() {

    }

    public void deleteUser() {

    }
}

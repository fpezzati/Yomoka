package edu.pezzati.yo.user;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.pezzati.yo.user.model.User;

public class UserServiceTest {

	@Before
	public void init() {

	}

	@After
	public void destroy() {

	}

	@Test
	public void createUser() {
		User user = new User();
		User expectedlUser = new User(UUID.randomUUID());
		UserPersistenceService persistenceService = Mockito.mock(UserPersistenceServiceImpl.class);
		UserService userService = new UserServiceImpl(persistenceService);
		Mockito.when(persistenceService.create(user)).thenReturn(expectedlUser);
		User actualUser = userService.create(user);
		Assert.assertEquals(expectedlUser, actualUser);
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

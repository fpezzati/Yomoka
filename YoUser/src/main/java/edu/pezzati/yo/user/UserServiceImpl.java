package edu.pezzati.yo.user;

import edu.pezzati.yo.user.model.User;

public class UserServiceImpl implements UserService {

	private UserPersistenceService persistenceService;

	public UserServiceImpl(UserPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@Override
	public User create(User user) {
		return persistenceService.create(user);
	}

	@Override
	public User update(User user) {
		return null;
	}
}

package edu.pezzati.yo.user.model;

import java.util.UUID;

public class User {

	private UUID id;

	public User() {
		// This POJO will become an entity sooner or later. That's why this
		// empty constructor.
	}

	public User(UUID id) {
		this.id = id;
	}

	public Object getId() {
		return id;
	}
}

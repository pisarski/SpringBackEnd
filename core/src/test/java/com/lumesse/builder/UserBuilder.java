package com.lumesse.builder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lumesse.entity.User;
import com.lumesse.entity.enums.UserRight;

public class UserBuilder {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean enabled = true;
	private Set<UserRight> rights = new HashSet<>();

	public UserBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public UserBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder withUsername(String username) {
		this.username = username;
		return this;
	}

	public UserBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder withEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public UserBuilder withRights(Set<UserRight> rights) {
		this.rights = rights;
		return this;
	}

	public UserBuilder withAnyValues() {
		this.enabled = true;
		this.firstName = "firstName";
		this.lastName = "lastName";
		this.username = "username";
		this.password = "password";
		this.rights = Stream.of(UserRight.values()).collect(Collectors.toSet());

		return this;
	}

	public User build() {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setId(id);
		user.setPassword(password);
		user.setUsername(username);
		user.setEnabled(enabled);
		user.setRights(rights);
		return user;
	}

}

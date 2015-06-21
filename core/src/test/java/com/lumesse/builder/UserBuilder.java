package com.lumesse.builder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lumesse.entity.User;
import com.lumesse.entity.enums.UserRole;

public class UserBuilder {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean enabled = true;
	private Set<UserRole> roles = new HashSet<>();

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

	public UserBuilder withRoles(Set<UserRole> roles) {
		this.roles = roles;
		return this;
	}

	public UserBuilder withAnyValues() {
		this.enabled = true;
		this.firstName = "firstName";
		this.lastName = "lastName";
		this.username = "username";
		this.password = "password";
		this.roles = Stream.of(UserRole.USER, UserRole.ADMIN).collect(
				Collectors.toSet());

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
		user.setRoles(roles);
		return user;
	}

}

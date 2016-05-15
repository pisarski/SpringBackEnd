package com.gmail.sebastian.pisarski.dto.user;

import java.util.Set;

import com.gmail.sebastian.pisarski.dto.BaseDto;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;

public class BasicUserDto extends BaseDto<User> {

	private String firstName;
	private String lastName;
	private String username;
	private Set<UserRight> rights;

	public BasicUserDto() {
		super(User.class);
	}

	public BasicUserDto(User user) {
		super(user, User.class);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<UserRight> getRights() {
		return rights;
	}

	public void setRights(Set<UserRight> rights) {
		this.rights = rights;
	}
}

package com.gmail.sebastian.pisarski.builder.dto.user;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.sebastian.pisarski.dto.user.BasicUserDto;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;

public class BasicUserDtoBuilder {

	private String firstName;
	private String lastName;
	private String username;
	private Set<UserRight> rights;

	public BasicUserDtoBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public BasicUserDtoBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public BasicUserDtoBuilder withUsername(String username) {
		this.username = username;
		return this;
	}

	public BasicUserDtoBuilder withRights(Set<UserRight> rights) {
		this.rights = rights;
		return this;
	}

	public BasicUserDtoBuilder withAnyValues() {
		this.firstName = "firstName";
		this.lastName = "lastName";
		this.username = "username";
		this.rights = Stream.of(UserRight.values()).collect(Collectors.toSet());

		return this;
	}

	public BasicUserDto build() {
		BasicUserDto dto = new BasicUserDto();
		setFields(dto);
		return dto;
	}

	protected void setFields(BasicUserDto dto) {
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setRights(rights);
		dto.setUsername(username);
	}

}

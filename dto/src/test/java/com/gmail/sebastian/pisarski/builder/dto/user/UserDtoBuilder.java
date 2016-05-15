package com.gmail.sebastian.pisarski.builder.dto.user;

import com.gmail.sebastian.pisarski.dto.user.UserDto;

public class UserDtoBuilder extends BasicUserDtoBuilder {

	private Long id;

	public UserDtoBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	@Override
	public UserDtoBuilder withAnyValues() {
		super.withAnyValues();
		return withId(8654L);
	}

	@Override
	public UserDto build() {
		UserDto dto = new UserDto();
		setFields(dto);
		dto.setId(id);
		return dto;
	}
}

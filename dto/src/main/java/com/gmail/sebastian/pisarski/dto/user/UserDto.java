package com.gmail.sebastian.pisarski.dto.user;

import com.gmail.sebastian.pisarski.entity.User;

public class UserDto extends BasicUserDto {

	private Long id;

	public UserDto() {
	}

	public UserDto(User user) {
		super(user);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

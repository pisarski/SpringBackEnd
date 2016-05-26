package com.gmail.sebastian.pisarski.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.gmail.sebastian.pisarski.entity.User;

@ApiModel(description = "Full information about spittle")
public class UserDto extends BasicUserDto {

	private Long id;

	public UserDto() {
	}

	public UserDto(User user) {
		super(user);
	}

	@ApiModelProperty(readOnly = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

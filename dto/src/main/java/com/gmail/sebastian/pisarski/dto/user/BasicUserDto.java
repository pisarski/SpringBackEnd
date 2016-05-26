package com.gmail.sebastian.pisarski.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import com.gmail.sebastian.pisarski.dto.BaseDto;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;

@ApiModel(description = "Contains all data used for adding/editing users")
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

	@ApiModelProperty(required = true)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@ApiModelProperty(required = true)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@ApiModelProperty(required = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@ApiModelProperty(required = true)
	public Set<UserRight> getRights() {
		return rights;
	}

	public void setRights(Set<UserRight> rights) {
		this.rights = rights;
	}
}

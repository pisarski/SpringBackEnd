package com.gmail.sebastian.pisarski.dto.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.gmail.sebastian.pisarski.builder.UserBuilder;
import com.gmail.sebastian.pisarski.builder.dto.user.UserDtoBuilder;
import com.gmail.sebastian.pisarski.entity.User;

public class UserDtoTest {

	@Test
	public void shouldProperlyMapUser() {
		// given
		User user = new UserBuilder().withAnyValues().build();

		// when
		UserDto dto = new UserDto(user);

		// then
		assertEquals(user.getFirstName(), dto.getFirstName());
		assertEquals(user.getId(), dto.getId());
		assertEquals(user.getLastName(), dto.getLastName());
		assertEquals(user.getRights(), dto.getRights());
		assertEquals(user.getUsername(), dto.getUsername());
	}

	@Test
	public void shouldReturnProperEntityFromDto() {
		// given
		UserDto dto = new UserDtoBuilder().withAnyValues().build();

		// when
		User user = dto.getEntity();

		// then
		assertEquals(dto.getFirstName(), user.getFirstName());
		assertEquals(dto.getLastName(), user.getLastName());
		assertEquals(dto.getId(), user.getId());
		assertEquals(dto.getRights(), user.getRights());
		assertEquals(true, user.isEnabled());
		assertEquals(dto.getUsername(), user.getUsername());
		assertNull(user.getPassword());
	}
}

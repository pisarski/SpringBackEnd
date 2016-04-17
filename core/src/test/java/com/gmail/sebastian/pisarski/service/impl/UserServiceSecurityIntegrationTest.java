package com.gmail.sebastian.pisarski.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;

import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;
import com.gmail.sebastian.pisarski.service.UserService;

public class UserServiceSecurityIntegrationTest extends
		AbstractSecurityIntegrationTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Autowired
	@Qualifier("userServiceAdapter")
	private UserService userService;

	@Test
	public void shouldInvokeFindAll() {
		invokeFindAll(UserRight.USER_MANAGEMENT);
	}

	@Test
	public void findAllShouldThrowException() {
		runWithParamsAndExpectedException(this::invokeFindAll,
				getAllRightsWithoutUserMgm(), AccessDeniedException.class);
	}

	@Test
	public void shouldInvokeGetNumberOfUser() {
		invokeGetNumberOfUsers(UserRight.USER_MANAGEMENT);
	}

	@Test
	public void getNumberOfUserShouldThrowException() {
		runWithParamsAndExpectedException(this::invokeGetNumberOfUsers,
				getAllRightsWithoutUserMgm(), AccessDeniedException.class);
	}

	@Test
	public void shouldInvokeFindByUsername() {
		invokeFindByUsername(UserRight.USER_MANAGEMENT);
	}

	@Test
	public void findByUsernameShouldThrowException() {
		runWithParamsAndExpectedException(this::invokeFindByUsername,
				getAllRightsWithoutUserMgm(), AccessDeniedException.class);
	}

	@Test
	public void shouldInvokeSave() {
		invokeSave(UserRight.USER_MANAGEMENT);
	}

	@Test
	public void shouldEditUser() {
		// given
		User user = new User();
		user.setId(85493L);
		user.setRights(Stream.of(UserRight.USER_MANAGEMENT).collect(
				Collectors.toSet()));
		loginAsUser(user);

		User toEdit = new User();
		toEdit.setId(432L);

		// when
		userService.save(toEdit);
	}

	@Test
	public void saveNewUserShouldThrowException() {
		runWithParamsAndExpectedException(this::invokeSave,
				getAllRightsWithoutUserMgm(), AccessDeniedException.class);
	}

	@Test
	public void userShouldNotBeAbleToEditHimself() {
		// given
		User user = new User();
		user.setId(85493L);
		user.setRights(Stream.of(UserRight.USER_MANAGEMENT).collect(
				Collectors.toSet()));
		loginAsUser(user);

		// then
		expected.expect(AccessDeniedException.class);

		// when
		userService.save(user);
	}

	@Test
	public void shouldInvokeGetById() {
		invokeGetById(UserRight.USER_MANAGEMENT);
	}

	@Test
	public void shouldGetUserOtherThanHimself() {
		// given
		User user = new User();
		user.setId(85493L);
		user.setRights(Stream.of(UserRight.USER_MANAGEMENT).collect(
				Collectors.toSet()));
		loginAsUser(user);

		long userId = 432L;

		// when
		User result = userService.getById(userId);

		// then
		assertNotNull(result);
	}

	@Test
	public void getByIdShouldThrowException() {
		runWithParamsAndExpectedException(this::invokeGetById,
				getAllRightsWithoutUserMgm(), AccessDeniedException.class);
	}

	@Test
	public void userShouldNotBeAbleToFindHimself() {
		// given
		User user = new User();
		user.setId(1L);
		user.setRights(Stream.of(UserRight.USER_MANAGEMENT).collect(
				Collectors.toSet()));
		loginAsUser(user);

		// then
		expected.expect(AccessDeniedException.class);

		// when
		userService.getById(user.getId());
	}

	private void invokeFindAll(Object right) {
		// given
		loginWithRights((UserRight) right);

		// when
		userService.findAll();
	}

	private void invokeGetNumberOfUsers(Object right) {
		// given
		loginWithRights((UserRight) right);

		// when
		userService.getNumberOfUsers();
	}

	private void invokeFindByUsername(Object right) {
		// given
		loginWithRights((UserRight) right);

		// when
		userService.findByUsername("");
	}

	private void invokeSave(Object right) {
		// given
		loginWithRights((UserRight) right);

		// when
		userService.save(new User());
	}

	private void invokeGetById(Object right) {
		// given
		loginWithRights((UserRight) right);

		// when
		userService.getById(876L);
	}

	private Object[] getAllRightsWithoutUserMgm() {
		return Stream.of(UserRight.values())
				.filter(right -> right != UserRight.USER_MANAGEMENT).toArray();
	}

}

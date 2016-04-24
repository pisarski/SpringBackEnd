package com.gmail.sebastian.pisarski.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;

import com.gmail.sebastian.pisarski.builder.SpittleBuilder;
import com.gmail.sebastian.pisarski.builder.UserBuilder;
import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.entity.User;
import com.gmail.sebastian.pisarski.entity.enums.UserRight;
import com.gmail.sebastian.pisarski.service.SpittleService;

public class SpittleServiceSecurityIntegrationTest extends
		AbstractSecurityIntegrationTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Autowired
	@Qualifier("spittleServiceAdapter")
	private SpittleService spittleService;

	@Test
	public void shouldInvokeFindAllSortedForNoLoggedUser() {
		spittleService.findAllSorted();
	}

	@Test
	public void shouldSaveNewSpittle() {
		// given
		loginWithRights(UserRight.ADD_SPITTLE);

		// when
		spittleService.save(new Spittle());

		// then
		assertTrue("No exception", true);
	}

	@Test
	public void shouldThrowExceptionForSaveNewSpittle() {
		Object[] rights = Stream.of(UserRight.values())
				.filter(right -> right != UserRight.ADD_SPITTLE).toArray();
		runWithParamsAndExpectedException(right -> {
			// given
				loginWithRights((UserRight) right);

				// when
				spittleService.save(new Spittle());

			}, rights, AccessDeniedException.class);
	}

	@Test
	public void shouldEditSpittle() {
		runWithParams(right -> {
			// given
				loginWithRights((UserRight) right);
				Spittle spittle = new SpittleBuilder().withId(75843L).build();

				// when
				spittleService.save(spittle);

				// then
				assertTrue("No exception", true);
			}, new UserRight[] { UserRight.EDIT_ALL_SPITTLES,
					UserRight.EDIT_OWN_SPITTLE });
	}

	@Test
	public void shouldThrowExceptionForEditSpittle() {
		Object[] rights = Stream
				.of(UserRight.values())
				.filter(right -> right != UserRight.EDIT_ALL_SPITTLES
						&& right != UserRight.EDIT_OWN_SPITTLE).toArray();
		runWithParamsAndExpectedException(right -> {
			// given
				loginWithRights((UserRight) right);
				Spittle spittle = new SpittleBuilder().withId(89L).build();

				// when
				spittleService.save(spittle);

			}, rights, AccessDeniedException.class);
	}

	@Test
	public void shouldGetSpittleById() {
		User createdUser = new UserBuilder()
			.withId(1L)
			.withRights(Stream.of(UserRight.EDIT_OWN_SPITTLE).collect(Collectors.toSet()))
			.build();

		User userWithEditAllRight = new UserBuilder()
				.withRights(Stream.of(UserRight.EDIT_ALL_SPITTLES).collect(Collectors.toSet()))
				.build();

		runWithParams(user -> {
			// given
				loginAsUser((User) user);

				// when
				Spittle result = spittleService.getById(78L);

				// then
				assertNotNull(result);
			}, new User[] { createdUser, userWithEditAllRight });
	}

	@Test
	public void getByIdShouldThrowExceptionForWrongRights() {
		Object[] rights = Stream
				.of(UserRight.values())
				.filter(right -> right != UserRight.EDIT_ALL_SPITTLES
						&& right != UserRight.EDIT_OWN_SPITTLE).toArray();
		runWithParamsAndExpectedException(right -> {
			// given
				User createdUser = new UserBuilder()
						.withId(1L)
						.withRights(Stream.of((UserRight) right).collect(Collectors.toSet()))
						.build();
				loginAsUser(createdUser);

				// when
				spittleService.getById(78L);
			}, rights, AccessDeniedException.class);
	}

}

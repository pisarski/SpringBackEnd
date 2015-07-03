package com.lumesse.service.impl;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lumesse.entity.Spittle;
import com.lumesse.entity.User;
import com.lumesse.entity.enums.UserRight;
import com.lumesse.exception.ErrorsContainer;
import com.lumesse.pojo.SpittleUserDetails;
import com.lumesse.repository.SpittleRepository;
import com.lumesse.service.SpittleService;

@RunWith(JUnitParamsRunner.class)
public class SplittleServiceImplTest {

	@Mock
	private SpittleRepository spittleRepository;

	@Mock
	private SpittleUserDetails userDetails;

	@InjectMocks
	@Spy
	private SpittleServiceImpl spittleService;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		setSecurityContext();
	}

	@Test
	public void shouldReturnAllSplittlesInProperOrder() {
		// given
		List<Spittle> spittles = new ArrayList<Spittle>();
		spittles.add(getSpittle(toDate("28-01-2015"), "M2"));
		spittles.add(getSpittle(toDate("01-01-2000"), "M0"));
		spittles.add(getSpittle(toDate("29-01-2015"), "M3"));
		spittles.add(getSpittle(toDate("06-06-2002"), "M1"));

		when(spittleRepository.findAll()).thenReturn(spittles);

		// when
		List<Spittle> result = spittleService.findAllSorted();

		// then
		assertThat(result, hasSize(4));
		for (int i = 0; i < result.size(); i++) {
			assertThat(result.get(i).getMessage(), equalTo("M"
					+ (result.size() - 1 - i)));
		}
	}

	@Test
	public void shouldSaveSpittle() {
		// given
		Spittle spittle = getSpittle(new Date(), "msg");
		Spittle savedSpittle = new Spittle();
		when(spittleRepository.save(spittle)).thenReturn(savedSpittle);

		// when
		Spittle result = spittleService.save(spittle);

		// then
		assertEquals(savedSpittle, result);
		verify(spittleRepository).save(spittle);
	}

	private void setSecurityContext() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities()));
	}

	@Test
	public void shouldSetTimeBeforeSave() {
		// given
		Spittle spittle = new Spittle();
		spittle.setMessage("message");
		spittle.setTitle("title");

		// when
		spittleService.save(spittle);

		// then
		ArgumentCaptor<Spittle> spittleCaptor = ArgumentCaptor
				.forClass(Spittle.class);
		verify(spittleRepository).save(spittleCaptor.capture());
		Spittle captured = spittleCaptor.getValue();
		assertNotNull(captured.getTime());
		assertEquals(spittle.getTitle(), captured.getTitle());
		assertEquals(spittle.getMessage(), captured.getMessage());
	}

	@Test
	public void shouldRemoveOldestSpittlesIfThreadsholdIsExceeded() {
		// given
		long numberOfSpittles = 30;
		assertTrue(numberOfSpittles > SpittleService.MAX_NUM_OF_SPITTLES);

		when(spittleRepository.count()).thenReturn(numberOfSpittles);
		@SuppressWarnings("unchecked")
		Page<Spittle> pageMock = mock(Page.class);
		List<Spittle> spittles = new ArrayList<>();
		when(pageMock.getContent()).thenReturn(spittles);
		when(spittleRepository.findAll(any(Pageable.class))).thenReturn(
				pageMock);

		// when
		spittleService.save(getSpittle(new Date(), "msg"));

		// then
		verify(spittleRepository).delete(spittles);
	}

	@Test
	public void getByIdShouldDelegateInvocation() {
		// given
		long id = 543L;
		Spittle spittle = new Spittle();
		when(spittleRepository.findOne(id)).thenReturn(spittle);

		// when
		Spittle result = spittleService.getById(id);

		// then
		assertEquals(spittle, result);
	}

	@Test
	@Parameters(method = "getParametersForShouldEditSpittle")
	public void shouldEditSpittle(User loggedUser) {
		// given
		disableValidation();
		long id = 6543L;
		Spittle spittle = getSpittle(new Date(), "msg");
		spittle.setId(id);
		spittle.setCreateUser(loggedUser);

		when(userDetails.getUser()).thenReturn(loggedUser);
		when(spittleRepository.findOne(id)).thenReturn(spittle);
		mockSave();

		Spittle editedSpittle = new Spittle();
		editedSpittle.setId(spittle.getId());
		editedSpittle.setMessage("editedMessage");
		editedSpittle.setTitle("editedTitle");

		// when
		Spittle saved = spittleService.save(editedSpittle);

		// then
		assertEquals(spittle.getId(), saved.getId());
		assertEquals(spittle.getCreateUser(), saved.getCreateUser());
		assertEquals(spittle.getTime(), saved.getTime());
		assertEquals(editedSpittle.getTitle(), saved.getTitle());
		assertEquals(editedSpittle.getMessage(), saved.getMessage());
		assertNotNull(saved.getUpdateTime());
		assertEquals(loggedUser, saved.getEditUser());
	}

	@Test
	@Parameters(method = "getParametersForShouldThrowExceptionIfUserNotPermitedToEdit")
	public void shouldThrowExceptionIfUserNotPermitedToEdit(User loggedUser) {
		// given
		long id = 6543L;
		Spittle spittle = new Spittle();
		User creationUser = new User();
		creationUser.setId(97658L);
		spittle.setCreateUser(creationUser);

		Spittle toSave = new Spittle();
		toSave.setId(id);

		when(userDetails.getUser()).thenReturn(loggedUser);
		when(spittleRepository.findOne(id)).thenReturn(spittle);

		// then
		expected.expect(AccessDeniedException.class);
		expected.expectMessage("User with id = " + loggedUser.getId()
				+ "donesn't have access to Spittle with id = "
				+ spittle.getId());

		// when
		spittleService.save(toSave);
	}

	private Spittle getSpittle(Date date, String msg) {
		Spittle spittle = new Spittle();
		spittle.setMessage(msg);
		spittle.setTime(date);
		spittle.setTitle("title");
		spittle.setCreateUser(new User());
		return spittle;
	}

	private Date toDate(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			return format.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void disableValidation() {
		doAnswer(args -> null).when(spittleService).validate(any(User.class),
				any(ErrorsContainer.class));
	}

	private void mockSave() {
		doAnswer(invocation -> invocation.getArgumentAt(0, Spittle.class))
				.when(spittleRepository).save(any(Spittle.class));
	}

	@SuppressWarnings("unused")
	private Object[] getParametersForShouldEditSpittle() {
		User editAllSpittlesUser = new User();
		editAllSpittlesUser.setRights(Stream.of(UserRight.EDIT_ALL_SPITTLES)
				.collect(Collectors.toSet()));

		User spittleCreator = new User();
		spittleCreator.setId(6584L);
		spittleCreator.setRights(Stream.of(UserRight.EDIT_OWN_SPITTLE).collect(
				Collectors.toSet()));

		return $(editAllSpittlesUser, spittleCreator);
	}

	@SuppressWarnings("unused")
	private Object[] getParametersForShouldThrowExceptionIfUserNotPermitedToEdit() {
		User noEditPermsUser = new User();
		noEditPermsUser.setRights(Stream
				.of(UserRight.values())
				.filter(right -> right != UserRight.EDIT_ALL_SPITTLES
						&& right != UserRight.EDIT_OWN_SPITTLE)
				.collect(Collectors.toSet()));

		User otherSpittleCreator = new User();
		otherSpittleCreator.setId(6584L);
		otherSpittleCreator.setRights(Stream.of(UserRight.EDIT_OWN_SPITTLE)
				.collect(Collectors.toSet()));

		return $(noEditPermsUser, otherSpittleCreator);
	}
}

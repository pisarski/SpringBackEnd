package com.gmail.sebastian.pisarski.rest;

import static com.gmail.sebastian.pisarski.rest.assertions.HeaderMatcher.containsHeaderWithValue;
import static com.gmail.sebastian.pisarski.rest.assertions.StatusMatcher.hasStatus;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.jboss.resteasy.mock.MockHttpRequest.get;
import static org.jboss.resteasy.mock.MockHttpRequest.post;
import static org.jboss.resteasy.mock.MockHttpRequest.put;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gmail.sebastian.pisarski.builder.SpittleBuilder;
import com.gmail.sebastian.pisarski.dto.spittle.BasicSpittleDto;
import com.gmail.sebastian.pisarski.dto.spittle.SpittleDto;
import com.gmail.sebastian.pisarski.entity.Spittle;
import com.gmail.sebastian.pisarski.exception.CustomError;
import com.gmail.sebastian.pisarski.exception.ValidationException;
import com.gmail.sebastian.pisarski.service.SpittleService;

@RunWith(MockitoJUnitRunner.class)
public class SpittleRestTest extends BaseRestTest {

	@InjectMocks
	private static SpittleRest spittleRest;

	@Mock
	private SpittleService spittleService;

	@Test
	public void shouldGetSpittles() throws Exception {
		// given
		Spittle spittle = new SpittleBuilder().withAllValuesInitialized().build();
		when(spittleService.findAllSorted()).thenReturn(asList(spittle));

		MockHttpRequest request = get("/spittles");

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.OK));
		assertThat(output, equalTo(jsonOf(asList(new SpittleDto(spittle)))));
	}

	@Test
	public void shouldGetSpittle() throws Exception {
		// given
		Spittle spittle = new SpittleBuilder().withAllValuesInitialized().build();
		when(spittleService.getById(spittle.getId())).thenReturn(spittle);

		MockHttpRequest request = get("/spittles/" + spittle.getId());

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.OK));
		assertThat(output, equalTo(jsonOf(new SpittleDto(spittle))));
	}

	@Test
	public void shouldNotFindSpittle() throws Exception {
		// given
		MockHttpRequest request = get("/spittles/-1");

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.NOT_FOUND));
		assertThat(output, equalTo(""));
	}

	@Test
	public void shouldReportValidationFailure() throws Exception {
		// given
		MockHttpRequest request = post("/spittles").content(jsonOf(new BasicSpittleDto()).getBytes())
				.contentType(MediaType.APPLICATION_JSON);

		CustomError customError = new CustomError("field", "errorCode", Collections.singletonMap("key", "value"),
				"rejectedValue");
		ValidationException validationException = new ValidationException(asList(customError));
		when(spittleService.save(any(Spittle.class))).thenThrow(validationException);

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.BAD_REQUEST));
		assertThat(response, containsHeaderWithValue("X-Status-Reason", "Validation failed"));
		assertThat(output, containsString(jsonOf(validationException.getCustomErrors())));
	}

	@Test
	public void shouldAddSpittle() throws Exception {
		// given
		MockHttpRequest request = post("/spittles").content(jsonOf(new BasicSpittleDto()).getBytes())
				.contentType(MediaType.APPLICATION_JSON);

		Spittle savedSpittle = new SpittleBuilder().withAllValuesInitialized().build();
		when(spittleService.save(any(Spittle.class))).thenReturn(savedSpittle);

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.CREATED));
		assertThat(response, containsHeaderWithValue("Location", "/spittles/" + savedSpittle.getId()));
		assertThat(output, equalTo(""));
	}

	@Test
	public void shouldUpdateSpittle() throws Exception {
		// given
		Spittle spittle = new SpittleBuilder().withAllValuesInitialized().build();
		Long idOfSpittleToUpdate = 653L;
		MockHttpRequest request = put("/spittles/" + idOfSpittleToUpdate)
				.content(jsonOf(new BasicSpittleDto(spittle)).getBytes()).contentType(MediaType.APPLICATION_JSON);

		// when
		MockHttpResponse response = invoke(request);

		// then
		String output = response.getContentAsString();
		assertThat(response, hasStatus(Status.NO_CONTENT));
		assertThat(output, equalTo(""));

		ArgumentCaptor<Spittle> spittleCaptor = ArgumentCaptor.forClass(Spittle.class);
		verify(spittleService).save(spittleCaptor.capture());
		Spittle updatedSpittle = spittleCaptor.getValue();

		assertEquals(idOfSpittleToUpdate, updatedSpittle.getId());
		assertEquals(spittle.getMessage(), updatedSpittle.getMessage());
		assertEquals(spittle.getTitle(), updatedSpittle.getTitle());
	}

	@Override
	protected void setUp() {
	}

	@Override
	protected Object getRestService() {
		return spittleRest;
	}
}

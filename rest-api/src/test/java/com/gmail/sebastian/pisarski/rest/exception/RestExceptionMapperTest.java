package com.gmail.sebastian.pisarski.rest.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.spi.Failure;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;

import com.gmail.sebastian.pisarski.exception.CustomError;
import com.gmail.sebastian.pisarski.exception.ValidationException;

public class RestExceptionMapperTest {

	private RestExceptionMapper mapper;

	@Before
	public void setUp() {
		mapper = new RestExceptionMapper();
	}

	@Test
	public void shouldHandleFailure() {
		// given
		Failure failure = mock(Failure.class);
		Response failureResponse = mock(Response.class);

		when(failure.getResponse()).thenReturn(failureResponse);

		// when
		Response response = mapper.toResponse(failure);

		// then
		assertEquals(failureResponse, response);
	}

	@Test
	public void shouldHandleWebApplicationException() {
		// given
		WebApplicationException exception = mock(WebApplicationException.class);
		Response exceptionResponse = mock(Response.class);

		when(exception.getResponse()).thenReturn(exceptionResponse);

		// when
		Response response = mapper.toResponse(exception);

		// then
		assertEquals(exceptionResponse, response);
	}

	@Test
	public void shouldHandleAccessDenied() {
		// given
		AccessDeniedException accessDenied = new AccessDeniedException("denied");

		// when
		Response response = mapper.toResponse(accessDenied);

		// then
		assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldHandleValidationException() {
		// given
		List<CustomError> errors = new ArrayList<>();
		ValidationException validationException = new ValidationException(errors);

		// when
		Response response = mapper.toResponse(validationException);

		// then
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals("Validation failed", response.getStringHeaders().get("X-Status-Reason").get(0));

		Map<String, Object> resultMap = (Map<String, Object>) response.getEntity();
		assertEquals("Validation failed", resultMap.get("Message"));
		assertEquals(validationException.getCustomErrors(), resultMap.get("ValidationErrors"));
	}

	@Test
	public void shouldHandleOtherExceptions() {
		// given
		Exception exception = new NullPointerException();

		// when
		Response response = mapper.toResponse(exception);

		// then
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		assertTrue(response.getEntity().toString().startsWith("Error occured:"));

	}
}

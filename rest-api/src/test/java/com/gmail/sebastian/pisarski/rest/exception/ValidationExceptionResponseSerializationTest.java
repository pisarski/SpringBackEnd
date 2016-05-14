package com.gmail.sebastian.pisarski.rest.exception;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;

import com.gmail.sebastian.pisarski.exception.CustomError;
import com.gmail.sebastian.pisarski.exception.ValidationException;
import com.gmail.sebastian.pisarski.rest.BaseRestTest;

public class ValidationExceptionResponseSerializationTest extends BaseRestTest {

	private static final String EXPECTED_JSON = "{\"Message\":\"Validation failed\",\"ValidationErrors\":[{\"field\":\"field\",\"errorCode\":\"errorCode\",\"messageVariables\":[\"rejectedValue\",\"value\"]}]}";
	private ValidationException exception;

	@Override
	protected void setUp() throws Exception {
		List<CustomError> errors = new ArrayList<>();
		Map<String, String> messageVariables = new HashMap<>();
		messageVariables.put("variable", "value");
		CustomError error = new CustomError("field", "errorCode", messageVariables, "rejectedValue");
		errors.add(error);
		exception = new ValidationException(errors);
	}

	@Override
	protected Object getRestService() {
		return new RestService();
	}

	@Path("exception")
	protected class RestService {

		@GET
		public void throwException() {
			throw exception;
		}

	}

	@Test
	public void shouldProperlySerializeErrorMessage() throws Exception {
		// given
		MockHttpRequest request = MockHttpRequest.get("/exception");

		// when
		MockHttpResponse response = invoke(request);

		// then
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		assertEquals(EXPECTED_JSON, response.getContentAsString());
	}

}

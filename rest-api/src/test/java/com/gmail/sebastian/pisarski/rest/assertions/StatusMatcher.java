package com.gmail.sebastian.pisarski.rest.assertions;

import javax.ws.rs.core.Response.Status;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.resteasy.mock.MockHttpResponse;

public class StatusMatcher {

	public static BaseMatcher<MockHttpResponse> hasStatus(Status status) {
		return new BaseMatcher<MockHttpResponse>() {

			@Override
			public void describeMismatch(Object item, Description description) {
				super.describeMismatch(Status.fromStatusCode(((MockHttpResponse) item).getStatus()), description);
			}

			@Override
			public void describeTo(Description arg) {
				arg.appendText("status " + status);
			}

			@Override
			public boolean matches(Object arg) {
				MockHttpResponse response = (MockHttpResponse) arg;
				return status.getStatusCode() == response.getStatus();
			}

		};
	}

}

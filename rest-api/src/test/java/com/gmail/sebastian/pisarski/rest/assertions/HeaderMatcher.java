package com.gmail.sebastian.pisarski.rest.assertions;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.resteasy.mock.MockHttpResponse;

public class HeaderMatcher {

	public static BaseMatcher<MockHttpResponse> containsHeaderWithValue(String header, String value) {
		return new BaseMatcher<MockHttpResponse>() {

			@Override
			public void describeMismatch(Object item, Description description) {
				super.describeMismatch(getAllHeaders((MockHttpResponse) item), description);
			}

			@Override
			public void describeTo(Description arg) {
				arg.appendText("header {" + header + ": [" + value + "]}");
			}

			@Override
			public boolean matches(Object arg) {
				MockHttpResponse response = (MockHttpResponse) arg;
				List<Object> headerValues = response.getOutputHeaders().get(header);
				if (headerValues == null) {
					return false;
				}
				for (Object headerValue : headerValues) {
					if (value.equals(String.valueOf(headerValue))) {
						return true;
					}
				}
				return false;
			}

			private String getAllHeaders(MockHttpResponse response) {
				MultivaluedMap<String, Object> headers = response.getOutputHeaders();
				StringBuilder builder = new StringBuilder();
				if (headers.size() > 1) {
					builder.append("[");
				}
				if (headers.containsKey(header)) {
					return "{" + header + ": " + headers.get(header) + "}";
				}
				for (Entry<String, List<Object>> entry : headers.entrySet()) {
					builder.append("{" + entry.getKey() + ": " + entry.getValue() + "}");
				}
				if (headers.size() > 1) {
					builder.append("]");
				}

				return builder.toString();
			}
		};
	}

}
